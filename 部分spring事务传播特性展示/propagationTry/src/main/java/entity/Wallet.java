package entity;

public class Wallet {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wallet.name
     *
     * @mbggenerated Thu Aug 30 13:26:12 CST 2018
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wallet.money
     *
     * @mbggenerated Thu Aug 30 13:26:12 CST 2018
     */
    private Integer money;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wallet.name
     *
     * @return the value of wallet.name
     *
     * @mbggenerated Thu Aug 30 13:26:12 CST 2018
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wallet.name
     *
     * @param name the value for wallet.name
     *
     * @mbggenerated Thu Aug 30 13:26:12 CST 2018
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wallet.money
     *
     * @return the value of wallet.money
     *
     * @mbggenerated Thu Aug 30 13:26:12 CST 2018
     */
    public Integer getMoney() {
        return money;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wallet.money
     *
     * @param money the value for wallet.money
     *
     * @mbggenerated Thu Aug 30 13:26:12 CST 2018
     */
    public void setMoney(Integer money) {
        this.money = money;
    }
}