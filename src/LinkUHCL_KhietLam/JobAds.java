/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LinkUHCL_KhietLam;

import java.util.ArrayList;


/**
 *
 * @author kitsune
 */
public class JobAds {
    private String jobId;
    private String creatorId;
    private String company;
    private String jobDesc;
    private String timestamp;
 
    
    JobAds(String id, String creatorId, String company, String desc, String tp){
        jobId=id;
        this.creatorId=creatorId;
        this.company=company;
        jobDesc=desc;
        timestamp=tp;
    }
    public String getJobAdId(){
    return jobId;
}
    @Override
    public String toString(){
        return (jobId + " - " + jobDesc + " - " + company + " created by " + creatorId +" - " + timestamp);
    }
  
    
}
