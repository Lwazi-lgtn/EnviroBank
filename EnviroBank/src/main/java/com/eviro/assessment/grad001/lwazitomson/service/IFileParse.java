package com.eviro.assessment.grad001.lwazitomson.service;

import java.io.File;
import java.net.URI;
import java.util.List;
import com.eviro.assessment.grad001.lwazitomson.entity.*;

public interface IFileParse {
    AccountProfile findByAccountDetails(String name, String surname, String url);
    List<AccountProfile> getAll();
    void save(AccountProfile accountProfile);
    void parseCSV(String[] csvFileLine);
    File convertCSVDataToImage(String base64ImageData);
    URI createImageLink(File fileImage);
}
