/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LinkUHCL_KhietLam;

import java.util.Scanner;

/**
 *
 * @author kitsune
 */
public class StartPage {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
         
        Scanner input=new Scanner(System.in);
        System.out.println("Welcome to LinkUHCL created by Khiet Lam");
        System.out.println("1. Sign up");
        System.out.println("2. Login");
        String userInput=input.nextLine();
        
        if (userInput.equals("1")) signup();

        else if (userInput.equals("2")) login();
          
    }
    
    public static void signup(){
       
        Scanner input=new Scanner(System.in);
        String pw, id;
        System.out.println("Choose account type: ");
        AccType.printTypeList();
        String type=input.nextLine();
        
        Validation verif;
        do{
        System.out.print("Username: ");
        id=input.nextLine();
        System.out.print("Password: ");
        pw=input.nextLine();
        verif=new Validation (id,pw);
        } while (!verif.isPass());
        
        System.out.print("Name: ");
        String name=input.nextLine();
        
        System.out.print("Company: ");
        String company=input.nextLine();
        DBM db=new DBM();
        db.createAcc(id,pw,name,company,type );
       
        
      
    }
    public static void login(){
        Scanner input=new Scanner(System.in);
        
        System.out.print("Username: ");
        String id=input.nextLine();
        
        System.out.print("Password: ");
        String pw=input.nextLine();
        DBM db=new DBM();
        int accstat=db.checkAcc(id,pw);
        if (accstat==1) {
            UserPortal newsession= new UserPortal(db.getAcc(id));
            newsession.accDisplay();
         
        }
        else if (accstat==2) System.out.println("Wrong password");
        else if (accstat==3) System.out.println("Account does not exist");
        else System.out.println("Internal Error");
    }
    
}
