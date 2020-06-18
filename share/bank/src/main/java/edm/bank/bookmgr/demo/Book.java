package edm.bank.bookmgr.demo;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
/** 
 * 
 * @author  author
 */
@Entity() @Table(name="book") public class Book {
  private String amount;
  private String name;
  private Integer id;
  private String state;
  private String type;
  public Book(){
  }
  public void setAmount(  String amount){
    this.amount=amount;
  }
  /** 
 * 
 */
  public String getAmount(){
    return this.amount;
  }
  public void setName(  String name){
    this.name=name;
  }
  /** 
 * 
 */
  public String getName(){
    return this.name;
  }
  public void setId(  Integer id){
    this.id=id;
  }
  /** 
 * 
 */
  @Id() public Integer getId(){
    return this.id;
  }
  public void setState(  String state){
    this.state=state;
  }
  /** 
 * 
 */
  public String getState(){
    return this.state;
  }
  public void setType(  String type){
    this.type=type;
  }
  /** 
 * 
 */
  public String getType(){
    return this.type;
  }
}
