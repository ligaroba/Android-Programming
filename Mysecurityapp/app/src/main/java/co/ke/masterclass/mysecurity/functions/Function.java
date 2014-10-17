package co.ke.masterclass.mysecurity.functions;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by root on 9/22/14.
 */
public class Function {
    private static final String TAG_VALUE="tag";
    private static final String SENT_TIP_TAG="sendtip";
    private static final String PERSON_TAG="persons";
    private static final String GET_ALL="getall";
    private static final String SET_PROFILE="profile";
    private static final String SET_REPORT_SEEN="reportSeen";
    private static final String REPORT_MISSING="reportmissing";
  public List<NameValuePair> Makereport(String crime, String username, String Description, String image, String video, String audio,
                                        String location,String emergencyCode){

      List<NameValuePair> map =new ArrayList<NameValuePair>();
      map.add(new BasicNameValuePair(TAG_VALUE,SENT_TIP_TAG ));
        map.add(new BasicNameValuePair("offence_type", crime));
        map.add(new BasicNameValuePair("username", username));
        map.add(new BasicNameValuePair("description",Description));
        map.add(new BasicNameValuePair("picture", image));
        map.add(new BasicNameValuePair("video", video));
        map.add(new BasicNameValuePair("audio", audio));
        map.add(new BasicNameValuePair("location", location));
        map.add(new BasicNameValuePair("status", emergencyCode));
     return map;



    }
    public List<NameValuePair> ReportMissing(String user_id,String names,String identity,String gender,String location,String picture,String video,String audio,String description,String status) {

        List<NameValuePair> map =new ArrayList<NameValuePair>();
        map.add(new BasicNameValuePair(TAG_VALUE,REPORT_MISSING ));
        map.add(new BasicNameValuePair("user",user_id));
        map.add(new BasicNameValuePair("identity",identity));
        map.add(new BasicNameValuePair("names",names));
        map.add(new BasicNameValuePair("description",description));
        map.add(new BasicNameValuePair("picture", picture));
        map.add(new BasicNameValuePair("video", video));
        map.add(new BasicNameValuePair("audio", audio));
        map.add(new BasicNameValuePair("location", location));
        map.add(new BasicNameValuePair("status",status));
        map.add(new BasicNameValuePair("gender",gender));
        return map;



    }
    public List<NameValuePair> PersonList(String status){

        List<NameValuePair> map =new ArrayList<NameValuePair>();
        map.add(new BasicNameValuePair(TAG_VALUE,PERSON_TAG ));
        map.add(new BasicNameValuePair("status", status));

        return map;

    }

    public List<NameValuePair> setProfile(String names, String email, String phone, String locality, String social,String gcm_regid){

        List<NameValuePair> map =new ArrayList<NameValuePair>();
        map.add(new BasicNameValuePair(TAG_VALUE,SET_PROFILE));
        map.add(new BasicNameValuePair("fullnames", names));
        map.add(new BasicNameValuePair("email", email));
        map.add(new BasicNameValuePair("tel",phone));
        map.add(new BasicNameValuePair("locality",locality));
        map.add(new BasicNameValuePair("social",social));
        map.add(new BasicNameValuePair("regId",gcm_regid));

        return map;



    }
    public List<NameValuePair> getAll(String tblname){

        List<NameValuePair> map =new ArrayList<NameValuePair>();
        map.add(new BasicNameValuePair(TAG_VALUE,GET_ALL));
        map.add(new BasicNameValuePair("tablename", tblname));


        return map;
    }
    public List<NameValuePair> reportSeen(String user,String dese,String personid,String location){

        List<NameValuePair> map =new ArrayList<NameValuePair>();
        map.add(new BasicNameValuePair(TAG_VALUE,SET_REPORT_SEEN));
        map.add(new BasicNameValuePair("user", user));
        map.add(new BasicNameValuePair("personid", personid));
        map.add(new BasicNameValuePair("location", location));
        map.add(new BasicNameValuePair("des", dese));
        return map;
    }

}
