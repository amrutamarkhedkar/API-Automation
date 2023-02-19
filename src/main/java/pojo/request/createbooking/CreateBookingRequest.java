package pojo.request.createbooking;

import java.util.Objects;

public class CreateBookingRequest{
    public String firstname;
    public String lastname;
    public int totalprice;
    public boolean depositpaid;
    public Bookingdates bookingdates;
    public String additionalneeds;
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public int getTotalprice() {
		return totalprice;
	}
	public void setTotalprice(int totalprice) {
		this.totalprice = totalprice;
	}
	public boolean isDepositpaid() {
		return depositpaid;
	}
	public void setDepositpaid(boolean depositpaid) {
		this.depositpaid = depositpaid;
	}
	
	public Bookingdates getBookingdates() {
		return bookingdates;
	}
	public void setBookingdates(Bookingdates bookingDatesObj) {
		this.bookingdates = bookingDatesObj;
	}
	public String getAdditionalneeds() {
		return additionalneeds;
	}
	public void setAdditionalneeds(String additionalneeds) {
		this.additionalneeds = additionalneeds;
	}
    
	@Override
	public int hashCode() {
		return Objects.hash(additionalneeds, bookingdates, depositpaid, firstname, lastname, totalprice);
	}
	@Override
	public boolean equals(Object obj) {
		
		CreateBookingRequest other = (CreateBookingRequest) obj;
		return Objects.equals(additionalneeds, other.additionalneeds)
				&& Objects.equals(bookingdates, other.bookingdates) && depositpaid == other.depositpaid
				&& Objects.equals(firstname, other.firstname) && Objects.equals(lastname, other.lastname)
				&& totalprice == other.totalprice;
	}
}