package com.abc.models;

public class Student {
	private long soCMND;
	private String hoTen;
	private String email;
	private long soDT;
	private String diaChi;
	private Graduation graduation;
	
	public Student() { this.graduation = new Graduation(); }
	
	public Student(long soCMND, String hoTen, String email, long soDT, String diaChi) {
		this.soCMND = soCMND;
		this.hoTen = hoTen;
		this.email = email;
		this.soDT = soDT;
		this.diaChi = diaChi;
		this.graduation = new Graduation();
	}

	public long getSoCMND() {
		return soCMND;
	}

	public void setSoCMND(Long soCMND) {
		this.soCMND = soCMND;
	}

	public String getHoTen() {
		return hoTen;
	}

	public void setHoTen(String hoTen) {
		this.hoTen = hoTen;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getSoDT() {
		return soDT;
	}

	public void setSoDT(Long soDT) {
		this.soDT = soDT;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}
	
	public Graduation getGraduation() {
        return graduation;
    }

    public void setGraduation(Graduation graduation) {
    	this.graduation = graduation != null ? graduation : new Graduation();
    }
}
