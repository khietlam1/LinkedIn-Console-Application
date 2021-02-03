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
public class UserPortal {
   
    private ArrayList<JobAds> sharedJobAds;
    DBM db=new DBM();
    private Account acc;
    private String accId;
    Scanner scanner=new Scanner(System.in);
    String input;
    
    public UserPortal(Account acc){
        if (acc instanceof Recruiter) this.acc=(Recruiter)acc;
        else this.acc=acc;
        accId=acc.getAccId();
        sharedJobAds=db.getSharedJobAds(accId);
    }
    
   private void showProfile(Account acc){
       acc.accSummary();
        System.out.println("---------------------------");
       System.out.println("Connections:");
       showConList(acc.getConns());
   }
    public void accDisplay(){
        //Including public profile and personal details of account
      
        System.out.println("Hello "+ acc.getName() + "!");
        showProfile(acc);
   
        System.out.println("---------------------------");
        System.out.println("Pending Request:");
        showConList(acc.getRequests());
        
        System.out.println("---------------------------");
        System.out.println("Your 2nd Connections:");
        showConList(acc.get2ndConns());
        
        System.out.println("---------------------------");
        System.out.println("Connections in your Company:");
        showConList(acc.getCompConns());
        
        System.out.println("---------------------------");
        System.out.println("Job Ads shared by Your Connections:");
       
        acc.showJobAds(sharedJobAds);
        
        actionList();
        
        
   }
    private void actionList(){
          System.out.println("---------------------------");
        System.out.println("ACTION LIST");
        System.out.println("(m) Manage contact");
        System.out.println("(s) Share a job ad");
        if (acc instanceof Recruiter)
            System.out.println("(c)Create a new Job Ad");
        input=scanner.nextLine();
        switch(input){
            case "m":
                manageContact();
                break;
            case "s":
                System.out.println("Choose a jobAd: ");
               shareJobAds();
                break;
            case "c":
                ((Recruiter)acc).createJobAds(acc);
                break;
                
        }
    }
   
    private void manageContact(){
    System.out.println("Choose a contact: ");
      String conName=scanner.nextLine();
      Account conAcc=db.getAcc(conName);
      if (conAcc!=null){
      boolean isConnected=false, isRequesting=false;
      if (db.getConnectedList(accId).contains(conName)) isConnected=true;
      if (db.getRequestList(accId).contains(conName)) isRequesting=true;
    
        System.out.println("(v) View " + conName + " profile");
        if (!isConnected && !isRequesting) System.out.println("(s) Send Request");
        else if (isRequesting) System.out.println("(r) Respond to Request");
       
        input=scanner.nextLine();
        if (input.equals("v"))
            showProfile(conAcc);
        if (input.equals("s"))
            sendRequest(conName);
        if(input.equals("r")){
            System.out.println("(a) Accept");
            System.out.println("(d) Decline");
            System.out.println("(i) Ignore");
            input=scanner.nextLine();
            switch (input){
                    case "a":
                        requestResponse(conName, "accepted");
                        break;
                    case "d":
                        requestResponse(conName, "declined");
                        break;
                    case "i":
                        requestResponse(conName, "ignored");
                        break;
            }           
        }
        
        }
        else System.out.println("No users found");
            
        //check relationship: connected, 2ndcon,compcon
        //show course of actions: view profile, invite
        //chose action
        //update action
    }

   private void sendRequest(String receiver){
     
      db.createConn(accId,receiver,"pending");
   }
   private void requestResponse(String sender, String stat){
       db.updateConn(sender,accId, stat);
      
   }
   
   
  
  
   private void shareJobAds(){
    String jobId=scanner.nextLine();
    ArrayList<String> adIds=db.getJobAdIdStr(sharedJobAds);
    if (adIds.contains(jobId))
        db.shareJobAds(jobId, accId);
    else System.out.println("No job ads found");
   }
   
     
     private void showConList(ArrayList<String> conn){
         conn.forEach(System.out::println);
     }
   
}
