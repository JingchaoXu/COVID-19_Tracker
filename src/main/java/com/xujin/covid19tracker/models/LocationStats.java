package com.xujin.covid19tracker.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocationStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long LocationId;

    private String state;
    private String country;

    //@NotBlank
    private int latestTotalCases;

    //@NotBlank
    private int diffFromPrevDay;
//
//    public int getDiffFromPrevDay() {
//        return diffFromPrevDay;
//    }
//
//    public void setDiffFromPrevDay(int diffFromPrevDay) {
//        this.diffFromPrevDay = diffFromPrevDay;
//    }
//
//    public String getState() {
//        return state;
//    }
//
//    public void setState(String state) {
//        this.state = state;
//    }
//
//    public String getCountry() {
//        return country;
//    }
//
//    public int getLatestTotalCases() {
//        return latestTotalCases;
//    }
//
//    public void setLatestTotalCases(int latestTotalCases) {
//        this.latestTotalCases = latestTotalCases;
//    }
//
//    public void setCountry(String country) {
//        this.country = country;
//    }
//
//    @Override
//    public String toString() {
//        return "LocationStats{" +
//                "state='" + state + '\'' +
//                ", country='" + country + '\'' +
//                ", latestTotalCases='" + latestTotalCases + '\'' +
//                '}';
//    }
}
