
package com.eviro.assessment.grad001.lwazitomson.service;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.eviro.assessment.grad001.lwazitomson.entity.AccountProfile;
import com.eviro.assessment.grad001.lwazitomson.repository.AccountProfileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileParse implements IFileParse {

    private final AccountProfileRepository accountProfileRepository = null;
    private final String SERVER_URI = "http://localhost:8080/";
    private String name;
    private String surname;
    private String imageFormat;

    @Override
    public AccountProfile findByAccountDetails(String name, String surname, String url) {
        name = name.replace("_X_", " ");
        surname = surname.replace("_X_", " ");

        // Find and return an AccountProfile from the accountProfileRepository based on provided account details.
        return accountProfileRepository.findByAccountHolderNameEqualsAndAccountHolderSurnameEqualsAndHttpImageLinkContaining(name, surname, url);
    }

    @Override
    public List<AccountProfile> getAll() {
        // Retrieve and return a list of all AccountProfile objects from the accountProfileRepository.
        return accountProfileRepository.findAll();
    }

    @Override
    public void save(AccountProfile accountProfile) {
        // Save the given AccountProfile object to the accountProfileRepository.
        accountProfileRepository.save(accountProfile);
    }

    @Override
    public void parseCSV(String[] csvFileLine) {
        // Parse CSV data and create an AccountProfile based on the provided data.

        // Extract relevant data from the CSV line.
        name = csvFileLine[0];
        surname = csvFileLine[1];
        imageFormat = csvFileLine[2];
        String imageData = csvFileLine[3];

        // Convert the base64 encoded image data to a File.
        File file = convertCSVDataToImage(imageData);

        // Create a URI from the File to be used as an HTTP image link.
        URI uri = createImageLink(file);

        // Build an AccountProfile object with the parsed data and save it to the repository.
        AccountProfile accountProfile = AccountProfile.builder()
                .accountHolderName(name)
                .accountHolderSurname(surname)
                .httpImageLink(buildURL(Paths.get(uri).getFileName().toString()))
                .build();

        save(accountProfile);
    }

    @Override
    public File convertCSVDataToImage(String base64ImageData) {
        // Convert base64 image data to a File and store it in a specified directory.

        // Decode the base64 image data to bytes.
        byte[] decodedBytes = Base64.getDecoder().decode(base64ImageData);

        // Generate a unique file name and file path.
        String fileName = String.format("%s.%s", UUID.randomUUID(), getFileExtension(imageFormat));
        String filePath = String.format("data/%s", fileName);

        try {
            // Write the decoded bytes to the file.
            FileOutputStream fos = new FileOutputStream(filePath);
            fos.write(decodedBytes);
            fos.close();
        } catch (Exception errorMessage) {
            errorMessage.printStackTrace();
            throw new RuntimeException("Folder NOT found");
        }

        // Return the created File object.
        return new File(filePath);
    }

    @Override
    public URI createImageLink(File file) {
        // Create a URI from the given File object.
        return file.toURI();
    }

    private String buildURL(String data) {
        // Build an HTTP image link URL with the provided data.
        name = name.replace(" ", "_X_");
        surname = surname.replace(" ", "_X_");
        return SERVER_URI + name + "/" + surname + "/" + data;
    }

    private String getFileExtension(String imageFormat) {
        // Extract the file extension from the image format (e.g., "image/jpeg").
        return imageFormat.split("/")[1];
    }

}
	

