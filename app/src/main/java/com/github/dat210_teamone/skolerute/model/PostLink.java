package com.github.dat210_teamone.skolerute.model;

import java.util.ArrayList;

/**
 * Created by Nicolas on 10.10.2016.
 * Part of project skolerute-android
 */

@SuppressWarnings("unused")
public class PostLink {
    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getPostNumber() {
        return postNumber;
    }

    public void setPostNumber(String postNumber) {
        this.postNumber = postNumber;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    private String postNumber;
    private double lat;
    private double lng;

    private PostLink(String postNumber, double lat, double lng) {
        this.postNumber = postNumber;
        this.lat = lat;
        this.lng = lng;
    }

    public static PostLink[] getDefaultArray()
    {
        ArrayList<PostLink> list = new ArrayList<>();
        list.add(new PostLink("4001",  58.90158640000001,  5.7123282));
        list.add(new PostLink("4002",  58.90158640000001,  5.7123282));
        list.add(new PostLink("4003",  58.90158640000001,  5.7123282));
        list.add(new PostLink("4004",  58.90158640000001,  5.7123282));
        list.add(new PostLink("4005",  58.9712195,  5.7272224));
        list.add(new PostLink("4006",  58.97397480000001,  5.729964));
        list.add(new PostLink("4007",  58.97956189999999,  5.7174781));
        list.add(new PostLink("4008",  58.9669476,  5.726252499999999));
        list.add(new PostLink("4009",  58.9624733,  5.713606));
        list.add(new PostLink("4010",  58.9617318,  5.7303561));
        list.add(new PostLink("4011",  58.9558506,  5.7332496));
        list.add(new PostLink("4012",  58.9633565,  5.7423595));
        list.add(new PostLink("4013",  58.9849459,  5.8224854));
        list.add(new PostLink("4014",  58.9691583,  5.7574359));
        list.add(new PostLink("4015",  58.9583506,  5.7740124));
        list.add(new PostLink("4016",  58.9454198,  5.7414499));
        list.add(new PostLink("4017",  58.9386071,  5.765267499999999));
        list.add(new PostLink("4018",  58.9273012,  5.7340151));
        list.add(new PostLink("4019",  58.9465331,  5.7163375));
        list.add(new PostLink("4020",  58.9176911,  5.7182542));
        list.add(new PostLink("4021",  58.94727239999999,  5.699599399999999));
        list.add(new PostLink("4022",  58.95940109999999,  5.6854303));
        list.add(new PostLink("4023",  58.9711619,  5.6796243));
        list.add(new PostLink("4024",  58.9712024,  5.711353));
        list.add(new PostLink("4025",  58.9747897,  5.695243899999999));
        list.add(new PostLink("4026",  58.9801171,  5.704909));
        list.add(new PostLink("4027",  58.9829236,  5.673813399999999));
        list.add(new PostLink("4028",  58.9897696,  5.6817005));
        list.add(new PostLink("4029",  59.0000168,  5.677660899999999));
        list.add(new PostLink("4031",  58.90158640000001,  5.7123282));
        list.add(new PostLink("4032",  58.9051919,  5.7407515));
        list.add(new PostLink("4033",  58.89343509999999,  5.7465205));
        list.add(new PostLink("4034",  58.89891050000001,  5.7203325));
        list.add(new PostLink("4035",  58.90158640000001,  5.7123282));
        list.add(new PostLink("4036",  58.90158640000001,  5.7123282));
        list.add(new PostLink("4041",  58.9464936,  5.684635099999999));
        list.add(new PostLink("4042",  58.95595580000001,  5.665638599999999));
        list.add(new PostLink("4043",  58.9538401,  5.648267));
        list.add(new PostLink("4044",  58.9369862,  5.671937));
        list.add(new PostLink("4045",  58.9380849,  5.6468526));
        list.add(new PostLink("4046",  58.9562674,  5.6255823));
        list.add(new PostLink("4047",  58.9672967,  5.636491299999999));
        list.add(new PostLink("4048",  58.9694753,  5.5862938));
        list.add(new PostLink("4049",  58.96802520000001,  5.619756));
        list.add(new PostLink("4064",  58.90158640000001,  5.7123282));
        list.add(new PostLink("4065",  58.90158640000001,  5.7123282));
        list.add(new PostLink("4066",  58.90158640000001,  5.7123282));
        list.add(new PostLink("4067",  58.90158640000001,  5.7123282));
        list.add(new PostLink("4068",  58.890985,  5.7373065));
        list.add(new PostLink("4069",  58.90158640000001,  5.7123282));
        list.add(new PostLink("4076",  58.99959699999999,  5.792171));
        list.add(new PostLink("4077",  58.9871208,  5.7316959));
        list.add(new PostLink("4078",  58.90158640000001,  5.7123282));
        list.add(new PostLink("4079",  58.90158640000001,  5.7123282));
        list.add(new PostLink("4081",  58.90158640000001,  5.7123282));
        list.add(new PostLink("4082",  58.90158640000001,  5.7123282));
        list.add(new PostLink("4083",  59.0046159,  5.7241446));
        list.add(new PostLink("4084",  58.90158640000001,  5.7123282));
        list.add(new PostLink("4085",  58.9974272,  5.7363545));
        list.add(new PostLink("4086",  59.00067199999999,  5.734768799999999));
        list.add(new PostLink("4087",  58.90158640000001,  5.7123282));
        list.add(new PostLink("4088",  58.90158640000001,  5.7123282));
        list.add(new PostLink("4089",  58.96073519999999,  5.7328085));
        list.add(new PostLink("4090",  58.96073519999999,  5.7328085));
        list.add(new PostLink("4091",  58.96073519999999,  5.7328085));
        list.add(new PostLink("4092",  58.90158640000001,  5.7123282));
        list.add(new PostLink("4093",  58.90158640000001,  5.7123282));
        list.add(new PostLink("4094",  58.90158640000001,  5.7123282));
        list.add(new PostLink("4095",  58.90158640000001,  5.7123282));
        list.add(new PostLink("4099",  58.90158640000001,  5.7123282));
        list.add(new PostLink("4154",  59.03829870000001,  5.7591962));
        PostLink[] links = new PostLink[list.size()];
        return list.toArray(links);
    }
}
