package com.example.infs3605_group_project.Dashboard;

/**
 * An object that holds the two possible filters for the
 * filterData method in DashboardActivity
 */
public class Filter {
    private String Year;
    private String Country;

    public Filter() {}
    public Filter(String year, String country) {
        Year = year;
        Country = country;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }
}
