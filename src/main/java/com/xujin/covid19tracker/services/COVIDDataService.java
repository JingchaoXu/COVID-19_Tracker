package com.xujin.covid19tracker.services;

import com.xujin.covid19tracker.Repo.LocationStatsAccessService;
import com.xujin.covid19tracker.models.LocationStats;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class COVIDDataService {

    private static String COVID_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    //private List<LocationStats> allStats = new ArrayList<>();

    private final LocationStatsAccessService locationStatsAccessService;

//    public List<LocationStats> getAllStats() {
//        return allStats;
//    }

    @Autowired
    public COVIDDataService(LocationStatsAccessService locationStatsAccessService){
        this.locationStatsAccessService = locationStatsAccessService;
    }


    @PostConstruct
    @Scheduled(cron="* * 1 * * *")
    @Transactional
    public void fetchVirusData() throws IOException, InterruptedException {
        List<LocationStats> newStats = new ArrayList<>();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(COVID_DATA_URL))
                .build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        //System.out.println(httpResponse.body());

        StringReader csvBodyReader = new StringReader(httpResponse.body());

        Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(csvBodyReader);

        for (CSVRecord record : records) {
            LocationStats locationStats = new LocationStats();
            locationStats.setCountry(record.get("Country/Region"));
            locationStats.setState(record.get("Province/State"));
            int latestDayNum = Integer.parseInt(record.get(record.size()-1));
            int pervDayNum = Integer.parseInt(record.get(record.size()-2));
            locationStats.setLatestTotalCases(latestDayNum);
            locationStats.setDiffFromPrevDay(latestDayNum-pervDayNum);

            //System.out.println(locationStats);
            locationStatsAccessService.save(locationStats);

        }

    }

//    @Transactional
//    public void updataData(){
//
//    }

    @Transactional(readOnly = true)
    public List<LocationStats> getData(){
        return locationStatsAccessService.findAll()
                .stream()
                .collect(Collectors.toList());
    }


}
