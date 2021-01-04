package talents.orange.challenge.controller.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiError implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private final LocalDateTime timeStamp = LocalDateTime.now();
    private Integer status;
    private String message;
}
