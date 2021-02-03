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
public abstract class Account {
    private String accId;
    private String accPw;
    private String name;
    private String company;
    
    DBM db=new DBM();
    public Account(String id, String pw, String name, String company ){
        setAccId(id);
        setAccPw(pw);
        this.name=name;
        this.company=company;
    }
    public void setAccId(String id){
       accId=id;
    }
    public void setAccPw(String pw){
      accPw=pw;
        
    }
    public String getAccId(){
        return accId;
    }
     public String getAccPw(){
        return accPw;
    }
    public abstract AccType getAccType();
    public String getName(){
       return name;
   }
    public String getCompany(){
       return company;
   }
   
    public ArrayList<String> getConns(){
         return db.getConnectedList(accId);
    }
    public ArrayList<String> get2ndConns(){
         return db.get2ndConnList(accId);
    }
     public ArrayList<String> getCompConns(){
         return db.getCompConList(accId);
    }
     public ArrayList<String> getRequests(){
         return db.getRequestList(accId);
    }
    
    public void showJobAds(ArrayList<JobAds> ads){
        if (ads!=null){
         for (JobAds job : ads){
             System.out.println(job.toString());
         }
        }
   }
    
    public void accSummary(){
       //general profile visible to public
       System.out.println("---------------------------");
       System.out.println("ACCOUNT SUMMARY");
       System.out.println("Name: "+name);
       System.out.println("Company: "+ company);
       System.out.println("Account type: "+ getAccType().name());
        
   }
    
}
