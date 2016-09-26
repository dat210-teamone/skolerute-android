package com.github.dat210_teamone.skolerute.data.dummy;

import com.github.dat210_teamone.skolerute.data.CsvReaderGetter;
import com.github.dat210_teamone.skolerute.data.ICsvGetter;

import java.io.BufferedReader;
import java.io.StringReader;

/**
 * Created by Nicolas on 26.09.2016.
 */

public class DummyCsvReaderGetter implements ICsvGetter {
    String schoolTester;
    String schoolDayTester;
    public DummyCsvReaderGetter(){
        schoolTester =
                "Nord,øst,Latitude,Longitude,ID,OBJTYPE,KOMM,BYGGTYP_NBR,INFORMASJON,Skolenavn,ADRESSE,Hjemmeside,ELEVER,KAPASITET \n" +
                "6538778.18,311643.10,58.946483,5.726794,39,Bygning,1103,613,Kommunal,Auglend skole,Hjalmar Johansens gate 32,http://www.minskole.no/auglend,ELEVER/TRINN 1.-68  2.-69  3.-86  4.-52  5.-52  6.-64  7.-57,22 klasserom \n" +
                "6535224.33,311389.82,58.915223,5.724265,40,Bygning,1103,613,Kommunal,Jåtten skole,Ordfører Askelandsgate 11,http://www.minskole.no/jaatten,ELEVER/TRINN 1.-79  2.-100  3.-101  4.-95  5.-76  6.-85  7.-69,28 klasserom \n" +
                "6544111.76,312468.58,58.995224,5.735939,41,Bygning,1103,614,Kommunal,Austbø skole,Austbøsvingene 50,http://www.minskole.no/austbo,ELEVER/TRINN 8.-138  9.-112  10.-134,15 klasserom \n" +
                "6538212.95,309221.50,58.940468,5.684139,42,Bygning,1103,614,Kommunal,Gosen skole,Sophus Bugges gate 13,http://www.minskole.no/gosen,ELEVER/TRINN 8.-103  9.-123  10.-103 (Gosen ATO skole 8.-11  9.-10  10.-10),12 klasserom ";

        schoolDayTester =
                "dato,skole,elevdag,laererdag,sfodag,kommentar\n" +
                "2016-09-01,Auglend skole,Ja,Ja,Ja,\n" +
                "2016-09-02,Auglend skole,Ja,Ja,Ja,\n" +
                "2016-09-03,Auglend skole,Nei,Nei,Nei,Lørdag\n" +
                "2016-09-04,Auglend skole,Nei,Nei,Nei,Søndag\n" +
                "2016-09-05,Auglend skole,Ja,Ja,Ja,\n" +
                "2016-09-06,Auglend skole,Ja,Ja,Ja,\n" +
                "2016-09-07,Auglend skole,Ja,Ja,Ja,\n" +
                "2016-09-08,Auglend skole,Ja,Ja,Ja,\n" +
                "2016-09-09,Auglend skole,Ja,Ja,Ja,\n" +
                "2016-09-10,Auglend skole,Nei,Nei,Nei,Lørdag";

    }

    @Override
    public BufferedReader getSchoolReader() {
        return new BufferedReader(new StringReader(schoolTester));
    }

    @Override
    public BufferedReader getSchoolDayReader() {
        return new BufferedReader(new StringReader(schoolDayTester));
    }
}
