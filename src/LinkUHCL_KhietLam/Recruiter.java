/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LinkUHCL_KhietLam;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author kitsune
 */
public class Recruiter extends Account {
    // to use createJobAds() or getJobAdsList(), need to get instance of Recruiter
     DBM db=new DBM();
    private final AccType type=AccType.Recruiter;
    private ArrayList <JobAds> myjobads;
    
    public Recruiter (String id, String pw, String name, String company){
        super(id,pw,name,company);
        setJobAdsList(id);
    }

   public void createJobAds(Account acc){
       //acc is the acccount holder who can create job ads
        System.out.print("JobId: ");
        Scanner scanner=new Scanner(System.in);
        String id=scanner.nextLine();
        System.out.print("Job Description: ");
        String jobdesc=scanner.nextLine();
        db.createJobAds(id, acc.getAccId(), acc.getCompany(), jobdesc);
             
    }
  private void setJobAdsList(String id){
      myjobads=db.getMyJobAds(id);
  }
  
  //only need this for acc summary
  public ArrayList<JobAds> getJobAdsList(){
    return myjobads;
} 
     @Override
  public void accSummary(){
    super.accSummary();
    System.out.println("Job Ads I created: ");
   super.showJobAds(myjobads);
  }
    @Override
    public AccType getAccType() {
        return type;
    }
}
