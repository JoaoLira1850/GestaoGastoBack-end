package controleGastos.Gestao.infra.excepitonController;


import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.Instant;
import java.util.List;

@RestControllerAdvice
public class ExcepitionController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExcepitionDTO> tratarErro400(MethodArgumentNotValidException ex,
                                                       HttpServletRequest request){

        List<FieldErrorDTO> listField = ex.getFieldErrors()
                .stream()
                .map(err -> new FieldErrorDTO(err.getField(), err.getDefaultMessage()))
                .toList();

        String message = "Erro de validação nos campos enviados.";

        ExcepitionDTO exx = new ExcepitionDTO(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                message,
                request.getRequestURI(),
                listField
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exx);


    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExcepitionDTO> tratarErro400Enum(HttpMessageNotReadableException ex,
                                                        HttpServletRequest request){



        String message = "JSON inválido. Verifique formato, enums e campos obrigatórios.";

        ExcepitionDTO exx = new ExcepitionDTO(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                message,
                request.getRequestURI(),
                List.of()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exx);

    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ExcepitionDTO> tratarErro400Path(MethodArgumentTypeMismatchException ex,
                                                           HttpServletRequest request){

        String message = "Verifique colocou um Valor não aceito na URL";

        ExcepitionDTO exx = new ExcepitionDTO(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                message,
                request.getRequestURI(),
                List.of()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exx);

    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ExcepitionDTO> tratarConstraintViolation(HandlerMethodValidationException ex,
                                                                   HttpServletRequest request){



        ExcepitionDTO body = new ExcepitionDTO(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Parametros Invalidos na Requisição",
                request.getRequestURI(),
                List.of()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);

    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExcepitionDTO> tratarErro404NotFound(EntityNotFoundException ex,
                                                               HttpServletRequest request){

        ExcepitionDTO body = new ExcepitionDTO(
                Instant.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI(),
                List.of()

        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);


    }


}
