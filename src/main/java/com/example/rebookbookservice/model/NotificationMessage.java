package com.example.rebookbookservice.model;

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationMessage implements Serializable {
    @NotBlank
    private String userId;

    @NotBlank
    private String content;

    @NotBlank
    private String type;

    @NotBlank
    private String relatedId;

}
