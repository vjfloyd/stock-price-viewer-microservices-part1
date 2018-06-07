package com.ennel.qr.model;

public class CAT_MCREDENCIAL {
	
	public String 	mcred_id;
	public String mcred_fecha_emision;
	public String mcred_estadocred;
	public String mcred_tipocred;
	
	public CAT_MCREDENCIAL(String mcred_id, String mcred_fecha_emision, String mcred_estadocred,
			String mcred_tipocred) {
		super();
		this.mcred_id = mcred_id;
		this.mcred_fecha_emision = mcred_fecha_emision;
		this.mcred_estadocred = mcred_estadocred;
		this.mcred_tipocred = mcred_tipocred;
	}
	
	
	public String getMcred_id() {
		return mcred_id;
	}
	public void setMcred_id(String mcred_id) {
		this.mcred_id = mcred_id;
	}
	public String getMcred_fecha_emision() {
		return mcred_fecha_emision;
	}
	public void setMcred_fecha_emision(String mcred_fecha_emision) {
		this.mcred_fecha_emision = mcred_fecha_emision;
	}
	public String getMcred_estadocred() {
		return mcred_estadocred;
	}
	public void setMcred_estadocred(String mcred_estadocred) {
		this.mcred_estadocred = mcred_estadocred;
	}
	public String getMcred_tipocred() {
		return mcred_tipocred;
	}
	public void setMcred_tipocred(String mcred_tipocred) {
		this.mcred_tipocred = mcred_tipocred;
	}
	
	
	
	
}
