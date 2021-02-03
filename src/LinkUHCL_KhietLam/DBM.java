/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LinkUHCL_KhietLam;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author kitsune
 */
public class DBM {
    ArrayList<JobAds> jobads= new ArrayList<JobAds>();
    ArrayList<String> jobids= new ArrayList<String>();
    
    Connection con=null;
    Statement st=null;
    ResultSet rs=null;
    final String url="jdbc:mysql://mis-sql.uhcl.edu/lamm1685?useSSL=false";
    final String db_id="lamm1685";
    final String db_pw="1373461";
    
    public void createAcc(String id, String pw, String name, String company, String type){
        try{
            con=DriverManager.getConnection(url,db_id,db_pw);
            st=con.createStatement();
            st.executeUpdate("Insert into account values('" + id + "', '" + pw + "', '" + 
                    name + "', '" + company + "', '" + type + "')");
            System.out.println("Account Created!");
        }
        catch(SQLException e){
          e.printStackTrace();
        }
        finally{
            try{
                
                con.close();
                st.close();
               // rs.close();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
    public int checkAcc(String id, String pw){
       try
       {
           
            con = DriverManager.getConnection(url, db_id, db_pw);
            st = con.createStatement();
            //search the accountID in the Account table
            rs = st.executeQuery("Select * from account " + "where accId = '" + id + "'");
            
            if(rs.next())
            {
                //the id is found, check the password
                if(pw.equals(rs.getString(2)))
                {
                    return 1;
                    
                }
                else
                {
                    //password is not correct
                    return 2;
                     
                }
            }
            else
            {
                return 3; //acc does not exist
            }
           
            
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return 4;
        }
        finally
        {
            //close the database
            try
            {
                con.close();
                st.close();
                rs.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            
        }
    }
   
    public Account getAcc(String id){
       Account acc=null;
        try{
           
        con = DriverManager.getConnection(url, db_id, db_pw);
        st = con.createStatement();
        rs = st.executeQuery("Select * from account " + "where accId = '" + id + "'");
        while(rs.next()){
            if (rs.getString("accType").equals("Recruiter")){
                acc=new Recruiter(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4));  
            }
            else {
                acc=new RegularAcc(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4));
            }
           
            }
       }
         catch (SQLException e)
        {
            e.printStackTrace();
            
        }
        finally
        {
            //close the database
            try
            {
                con.close();
                st.close();
                rs.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            
        }
        return acc;
        
        
    }
   
    public void createConn(String sender, String receiver, String stat){
        try{
            con=DriverManager.getConnection(url,db_id,db_pw);
            st=con.createStatement();
            st.executeUpdate("Insert into connections values('" + sender + "', '" + receiver + "', '" + stat + "')");
            System.out.println("Request sent!");
        }
        catch(SQLException e){
          e.printStackTrace();
        }
        finally{
            try{
                
                con.close();
                st.close();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
    public void updateConn(String sender, String receiver, String stat){
    try
        {
            con=DriverManager.getConnection(url,db_id,db_pw);
            st=con.createStatement();
            con.setAutoCommit(false);
            st.executeUpdate("Update connections set reqStatus = '" + stat + "'"
                            + "where sender='"+ sender + "' && receiver='" + receiver +"'");
            
            con.commit();
            con.setAutoCommit(true);
            System.out.println("Request " + stat);

        }
        catch (SQLException e)
        {
            //handle the exception
            e.printStackTrace();
        }
        finally
        {
            //close the database
            try
            {
                st.close();
                con.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    
    public ArrayList<String> getRequestList (String receiver){
        try{
            con=DriverManager.getConnection(url,db_id,db_pw);
            st=con.createStatement();
            rs = st.executeQuery("Select * from connections where (receiver = '" + receiver +"'&& reqStatus='pending')");
            //Select * from connections where receiver='receiver' && reqStatus='pending'
            ArrayList<String> requests= new ArrayList<String>();
            while(rs.next()){
               
                requests.add(rs.getString("sender"));
            }
            return requests;
        }
        catch(SQLException e){
          e.printStackTrace();
          return null;
        }
        finally{
            try{
                
                con.close();
                st.close();
                rs.close();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
    public ArrayList<String> getConnectedList (String id){
        try{
            con=DriverManager.getConnection(url,db_id,db_pw);
            st=con.createStatement();
            rs = st.executeQuery("Select * from connections where ((receiver ='" + id + "' || sender='" + id + "')" 
                    + " && reqStatus='accepted')");
            
            ArrayList<String> lists= new ArrayList<String>();
            while(rs.next()){
                if (rs.getString("receiver").equals(id))
                    lists.add(rs.getString("sender"));
                else 
                    lists.add(rs.getString("receiver"));
            }
            return lists;
        }
        catch(SQLException e){
          e.printStackTrace();
          return null;
        }
        finally{
            try{
                
                con.close();
                st.close();
                rs.close();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
    public ArrayList<String> get2ndConnList (String id){
        ArrayList<String> connected=getConnectedList(id);
        ArrayList<String> lists= new ArrayList<String>();
        
        try{
            con=DriverManager.getConnection(url,db_id,db_pw);
            st=con.createStatement();
            for(String con : connected){

                
            rs = st.executeQuery("Select * from connections where ((receiver ='" + con + "' || sender='" + con + "')" 
                    + " && reqStatus='accepted')");
            
           
            while(rs.next()){
                if (!rs.getString("receiver").equals(id) && !rs.getString("sender").equals(id)){
                    if (con.equals(rs.getString("sender")) && !connected.contains(rs.getString("receiver")) ) 
                        lists.add(rs.getString("receiver"));
                    else if (con.equals(rs.getString("receiver")) && !connected.contains(rs.getString("sender"))) {
                            lists.add(rs.getString("sender"));
                    }
                    
                }
            }
            }
            return lists;
        }
        catch(SQLException e){
          e.printStackTrace();
          return null;
        }
        finally{
            try{
                
                con.close();
                st.close();
                rs.close();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    //public Connections getCon(String id)
    
    public ArrayList<String> getCompConList (String id){
        try{
            String comp=getCompany(id);
            con=DriverManager.getConnection(url,db_id,db_pw);
            st=con.createStatement();
            rs = st.executeQuery("Select * from account where company='" + comp + "'");
          
            ArrayList<String> lists= new ArrayList<String>();
           
            while(rs.next()){
                if(!rs.getString("accId").equals(id))
                    lists.add(rs.getString("accId"));
            }
            return lists;
        }
        catch(SQLException e){
          e.printStackTrace();
          return null;
        }
        finally{
            try{
                
                con.close();
                st.close();
                rs.close();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
    public String getCompany(String id)
    {
        try
        {
            //connect to the database
            con = DriverManager.getConnection(url, db_id, db_pw);
            st = con.createStatement();
            con.setAutoCommit(false);
            rs = st.executeQuery("select * from account "
                    + "where accId = '" + id + "'");
            
            con.commit();
            con.setAutoCommit(true);
            
            if(rs.next())
            {
                return rs.getString("company");
            }
            else
            {
                return null;
            }
        }
        catch (SQLException e)
        {
            //handle the exception
            e.printStackTrace();
            return null;
        }
        finally
        {
            //close the database
            try
            {
                rs.close();
                st.close();
                con.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
 
    public void createJobAds(String jobId, String creatorId, String company, String jobDesc){
        try{
            con=DriverManager.getConnection(url,db_id,db_pw);
            st=con.createStatement();
            st.executeUpdate("Insert into jobads values('" + jobId + "', '" + creatorId + "', '" 
                    + company + "', '" + jobDesc + "', '" + Timestamp.timestamp() +"')");
            System.out.println("Job Ad posted!");
           
        }
        catch(SQLException e){
          e.printStackTrace();
         
        }
        finally{
            try{
                
                con.close();
                st.close();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public void shareJobAds(String jobId, String accId){
        try{
            con=DriverManager.getConnection(url,db_id,db_pw);
            st=con.createStatement();
            st.executeUpdate("Insert into jobshare values('" + accId + "', '" + jobId + "')");
            System.out.println("Job Ad shared!");
        }
        catch(SQLException e){
          e.printStackTrace();
        }
        finally{
            try{
                
                con.close();
                st.close();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
public JobAds getJobAd (String jobId){
    JobAds ad=null;
    try{
        con=DriverManager.getConnection(url,db_id, db_pw);
        st=con.createStatement();
        rs=st.executeQuery("Select * from jobads where jobId='"+ jobId + "'");
        if (rs.next()){
            ad=new JobAds(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5));
            
        }
            
    }
    catch (SQLException e){
        e.printStackTrace();
        return null;
    }
    finally{
        try{
        con.close();
        st.close();
        rs.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        
    }
    return ad;
}
 public ArrayList<String> getJobAdIdStr(ArrayList<JobAds> ads){
       ArrayList<String> adIds=new ArrayList<String>();
       for (JobAds ad : ads){
           adIds.add(ad.getJobAdId());
       }
       return adIds;
   }

public ArrayList<String> getJobAdId(String id){
    ArrayList<String> conns= getConnectedList(id);
  
      try{
            con=DriverManager.getConnection(url,db_id,db_pw);
            st=con.createStatement();
            for (String con : conns){
              
               //get jobids shared by contacts
                rs = st.executeQuery("Select * from jobshare where accId ='" + con +"'");
                
                while(rs.next()){
                    jobids.add(rs.getString("jobId"));
                }
                //get jobids created by contacts who are recruiters
                rs = st.executeQuery("Select * from jobads where creatorId ='" + con +"'");
                while(rs.next()){
                    //need to test2 //
                        jobids.add(rs.getString("jobId"));
                }
                
            }
            return jobids;
        }
        catch(SQLException e){
          e.printStackTrace();
          return null;
        }
        finally{
            try{
                
                con.close();
                st.close();
                rs.close();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
}

public ArrayList<JobAds> getMyJobAds(String id){
   //get self created job ads
    ArrayList <JobAds> myads= new ArrayList<JobAds>();
    try{
       con=DriverManager.getConnection(url,db_id,db_pw);
       st=con.createStatement();
       rs=st.executeQuery("Select * from jobads where creatorId='"+ id + "'");
       while(rs.next()){
            myads.add( new JobAds(rs.getString("jobId"),
                            rs.getString("creatorId"),
                            rs.getString("company"), 
                            rs.getString("jobDesc"),
                            rs.getString("timestamp")));
       }
       return myads;
    }
    catch (SQLException e){
        e.printStackTrace();
        return null;
    }
    finally{
        try{
        con.close();
        st.close();
        rs.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
}
public ArrayList<JobAds> getSharedJobAds(String id){
 jobids= getJobAdId(id);
      try{
            con=DriverManager.getConnection(url,db_id,db_pw);
            st=con.createStatement();
            for (String jobid : jobids){
                
                rs = st.executeQuery("Select * from jobads where jobId ='" + jobid +"'");

                while(rs.next()){
                    String jobId=rs.getString("jobId");
                    ArrayList<String> adIds=getJobAdIdStr(jobads);
                    if (!adIds.contains(jobId))
                    jobads.add( new JobAds(rs.getString("jobId"),
                            rs.getString("creatorId"),
                            rs.getString("company"), 
                            rs.getString("jobDesc"),
                            rs.getString("timestamp")));
                }
            }
            return jobads;
        }
        catch(SQLException e){
          e.printStackTrace();
          return null;
        }
        finally{
            try{
                
                con.close();
                st.close();
                rs.close();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
}

}
   
    
    

