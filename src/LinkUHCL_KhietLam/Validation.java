/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LinkUHCL_KhietLam;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author kitsune
 */
public class Validation {
    private String id;
    private String pw;
    public Validation(String id, String pw){
    setId(id);
    setPw(pw);
}
    public void setId(String id){
        this.id=id;
    }
    public void setPw(String pw){
        this.pw=pw;
    }
    public boolean pwValidation(){
          String requirements = "^(?=.*[0-9])"
                                + "(?=.*[a-z])"
                                + "(?=.*[#?!*])"
                                + "(?=\\S+$).{4,8}$"; 
          Pattern pwPattern = Pattern.compile(requirements);
          Matcher pwCheck = pwPattern.matcher(pw);
          if (!pwCheck.matches()) System.out.println("Invalid password");
          else if (pw.equals(id)) System.out.println("Password can't be the same as account id");
          else return pwCheck.matches();
          return false;
    }
    public boolean idValidation(){
          String requirements = "^[a-z]\\w{3,10}$";
          Pattern idPattern = Pattern.compile(requirements);
          Matcher idCheck = idPattern.matcher(id);
          if (!idCheck.matches()) System.out.println("Invalid id");
          return idCheck.matches();
    }
    public boolean isPass(){
        return pwValidation() && idValidation();
    }
}
