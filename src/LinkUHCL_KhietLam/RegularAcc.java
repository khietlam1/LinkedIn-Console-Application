/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LinkUHCL_KhietLam;

/**
 *
 * @author kitsune
 */
public class RegularAcc extends Account {
     private final AccType type=AccType.Regular;
    public RegularAcc (String id, String pw, String name, String company){
        super(id,pw,name,company);
    }

    @Override
    public AccType getAccType() {
      return type;
    }
}
