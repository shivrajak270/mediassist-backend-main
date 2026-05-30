package com.shivraj.medassist.Dto;



import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class MailDTO implements Serializable {
    private String to;
    private String medicineName;
    private String userName;
    private String userEmail;

}

