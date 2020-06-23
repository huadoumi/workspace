package edm.Agree.bookMgr.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class Book {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column book.id
     *
     * @mbggenerated Mon Sep 09 17:11:30 CST 2019
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column book.name
     *
     * @mbggenerated Mon Sep 09 17:11:30 CST 2019
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column book.type
     *
     * @mbggenerated Mon Sep 09 17:11:30 CST 2019
     */
    private String type;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column book.amount
     *
     * @mbggenerated Mon Sep 09 17:11:30 CST 2019
     */
    private String amount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column book.state
     *
     * @mbggenerated Mon Sep 09 17:11:30 CST 2019
     */
    private String state;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column book.id
     *
     * @return the value of book.id
     *
     * @mbggenerated Mon Sep 09 17:11:30 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column book.id
     *
     * @param id the value for book.id
     *
     * @mbggenerated Mon Sep 09 17:11:30 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column book.name
     *
     * @return the value of book.name
     *
     * @mbggenerated Mon Sep 09 17:11:30 CST 2019
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column book.name
     *
     * @param name the value for book.name
     *
     * @mbggenerated Mon Sep 09 17:11:30 CST 2019
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column book.type
     *
     * @return the value of book.type
     *
     * @mbggenerated Mon Sep 09 17:11:30 CST 2019
     */
    public String getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column book.type
     *
     * @param type the value for book.type
     *
     * @mbggenerated Mon Sep 09 17:11:30 CST 2019
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column book.amount
     *
     * @return the value of book.amount
     *
     * @mbggenerated Mon Sep 09 17:11:30 CST 2019
     */
    public String getAmount() {
        return amount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column book.amount
     *
     * @param amount the value for book.amount
     *
     * @mbggenerated Mon Sep 09 17:11:30 CST 2019
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column book.state
     *
     * @return the value of book.state
     *
     * @mbggenerated Mon Sep 09 17:11:30 CST 2019
     */
    public String getState() {
        return state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column book.state
     *
     * @param state the value for book.state
     *
     * @mbggenerated Mon Sep 09 17:11:30 CST 2019
     */
    public void setState(String state) {
        this.state = state;
    }
    
    public static void main(String[] args) {
    	JSONObject json = new JSONObject();
    	json.put("name", "pang");
    	json.put("age", "28");
	}
}