package org.example.server.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseDto implements Serializable {
    @Serial
    private static final long serialVersionUID  = 12162716L;

    private boolean worked =false ;
    private Object result = null;
    private String message;
}
