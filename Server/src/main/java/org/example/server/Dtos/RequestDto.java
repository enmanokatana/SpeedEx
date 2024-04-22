package org.example.server.Dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@NoArgsConstructor
@AllArgsConstructor
 @Builder


public class RequestDto {
    private  Object Body;
    private Object header = null;

}
