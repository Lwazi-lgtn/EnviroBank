package com.eviro.assessment.grad001.lwazitomson.controller;

import java.awt.PageAttributes.MediaType;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


import com.eviro.assessment.grad001.lwazitomson.entity.AccountProfile;
import com.eviro.assessment.grad001.lwazitomson.service.IFileParse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController("/")
public class ImageController {
	// Declare a File Parser object (IFileParse interface) with null initialization.
    // It is recommended to initialize it with an actual implementation of IFileParse before using it.
    private final IFileParse fileParse = null;

 // Mapping for the "start" endpoint. This method is called when "start" is accessed via HTTP GET.
    // It reads data from the CSV file and returns "Success".
    @GetMapping("start")
    public String uploadData() {
        readData();// Calls the readData() method to read data from the CSV file.
        return "Success";
    }

    @GetMapping("all")
    public List<AccountProfile> getAll() {
        return fileParse.getAll();
    }
 // Mapping for the "{name}/{surname}/{uri}" endpoint, where {name}, {surname}, and {uri} are path variables.
    // This method returns an image file as a FileSystemResource based on the provided account details.
    @GetMapping(value = "{name}/{surname}/{uri}", produces = MediaType.IMAGE_JPEG_VALUE)
    public FileSystemResource getHttpImageLink(@PathVariable String name,
                                               @PathVariable String surname,
                                               @PathVariable String uri){

        AccountProfile accountProfile = fileParse.findByAccountDetails(name, surname, uri);
//No results exception
        if(accountProfile == null){
            System.out.println("Details are not there");
            return null;
        }


        String link = accountProfile.getHttpImageLink();
        String localDir = "data/" + link.substring(link.lastIndexOf("/") + 1);
        File file = new File(localDir);

        return new FileSystemResource(file);
    }

    private void readData(){
        // Path to the CSV file
        String csvFilePath = "C:\\Users\\lwazi\\Downloads\\1672815113084-GraduateDev_AssessmentCsv_Ref003.csv";

        try (CSVReader reader = new CSVReader(new FileReader(csvFilePath))) {
            String[] header = reader.readNext();

            String[] line;
            while ((line = reader.readNext()) != null) {
                fileParse.parseCSV(line);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }

}