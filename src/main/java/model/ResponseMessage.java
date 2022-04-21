package model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseMessage {
    private Boolean isSuccessful;
    private String message;
    private Integer id;
}
