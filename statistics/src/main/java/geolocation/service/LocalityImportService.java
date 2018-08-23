package geolocation.service;

import geolocation.model.Locality;
import org.apache.commons.io.IOUtils;
import org.rauschig.jarchivelib.Compressor;
import org.rauschig.jarchivelib.CompressorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

@Service
public class LocalityImportService {

    private static final Logger log = LoggerFactory.getLogger(LocalityImportService.class);

    private final String path;

    private final ResourceLoader resourceLoader;

    @Autowired
    public LocalityImportService(ResourceLoader resourceLoader, @Value("${geolocation.path}") String path) {
        this.resourceLoader = resourceLoader;
        this.path = path;
    }


    public void extractDictionary(File archive, File dictionary) {
        log.debug("extractDictionary() - start");
        Compressor compressor = CompressorFactory.createCompressor(archive);
        try {
            compressor.decompress(archive, dictionary);
        } catch (IOException e) {
            log.error("extractDictionary() - Error", e);
        }
        log.debug("extractDictionary() - end");
    }

    public void removeDictionary(File dictionary) throws IOException {
        log.debug("removeDictionary() - start");
        boolean result = Files.deleteIfExists(dictionary.toPath());

        if (result) {
            log.debug("removeDictionary() - end");
            return;
        }
        log.debug("removeDictionary() - Error: Dictionary doesn't exist");
    }

    public List<Locality> getLocalitiesFromDictionary() throws IOException {
        log.debug("getLocalitiesFromDictionary() - start");

        InputStream archiveIs = resourceLoader.getResource(path + "dictionary_of_localities.gz").getInputStream();
        File tempArchive = File.createTempFile("dictionary_of_localities_temp", ".gz");
        try (FileOutputStream out = new FileOutputStream(tempArchive)) {
            IOUtils.copy(archiveIs, out);
        }
        File dictionary = File.createTempFile("dictionary_of_localities", ".txt");
        extractDictionary(tempArchive, dictionary);

        List<Locality> localities = new ArrayList<>();

        Boolean isSecondLine = false;

        try (Scanner scanner = new Scanner(dictionary, "ISO-8859-1")) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (isSecondLine) {
                    List<String> city = new LinkedList<>(Arrays.asList(line.split(",")));
                    city.remove(4);
                    city.remove(1);
                    city.add(0, UUID.randomUUID().toString());
                    Collections.swap(city, 1, 2);

                    int number = 0;
                    try {
                        if (city.get(3) != null)
                            number = Integer.parseInt(city.get(3));
                    } catch (NumberFormatException e) {
                        number = 0;
                    }

                    Locality locality = new Locality();
                    locality.setId(city.get(0));
                    locality.setName(city.get(1));
                    locality.setCountry(city.get(2).toUpperCase());
                    locality.setRegion(number);
                    locality.setLatitude(Double.parseDouble(city.get(4)));
                    locality.setLongtitude(Double.parseDouble(city.get(5)));

                    localities.add(locality);
                }
                isSecondLine = true;
            }
        } catch (FileNotFoundException e) {
            log.error("extractDictionary() - Error", e);
        }
        removeDictionary(dictionary);
        log.debug("getLocalitiesFromDictionary() - end");
        return localities;
    }
}
