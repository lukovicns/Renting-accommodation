package com.project.web_service.wrappers.responses;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "getAccommodationsResponse", namespace = "http://com.project/web_service/wrappers")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getAccommodationsResponse", namespace = "http://com.project/web_service/wrappers")
public class GetAccommodationsResponse {

	@XmlElement(name = "return")
    private String _return;

    public String getReturn() {
        return this._return;
    }

    public void setReturn(String new_return)  {
        this._return = new_return;
    }
}