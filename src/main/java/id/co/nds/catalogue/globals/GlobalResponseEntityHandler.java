package id.co.nds.catalogue.globals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import id.co.nds.catalogue.exceptions.ClientException;
import id.co.nds.catalogue.models.ResponseModel;

@ControllerAdvice
@RestController
public class GlobalResponseEntityHandler extends ResponseEntityExceptionHandler {

    //Client
    @ExceptionHandler({ClientException.class})
    public ResponseEntity<Object> handleClientException(ClientException ex, WebRequest request) {
        Map<String, String> data = new HashMap<>();
        data.put("error", ex.getMessage());

        ResponseModel response = new ResponseModel();
        response.setMsg("Client Error");
        response.setData(data);

        return ResponseEntity.badRequest().body(response);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, List<String>> data = new HashMap<>();

        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        
        if(errors.size() > 1) {
            data.put("errors", errors);
        }
        else {
            data.put("error", errors);
        }

        ResponseModel response = new ResponseModel();
        response.setMsg("Client Error");
        response.setData(data);

        return ResponseEntity.badRequest().body(response);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> data = new HashMap<>();
        data.put("error", ex.getMessage());

        ResponseModel response = new ResponseModel();
        response.setMsg("Client Error");
        response.setData(data);

        return ResponseEntity.badRequest().body(response);
    }

    //Not Found Error
    @ExceptionHandler({NoSuchElementException.class})
    public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException ex, WebRequest request) {
        Map<String, String> data = new HashMap<>();
        data.put("error", ex.getMessage());

        ResponseModel response = new ResponseModel();
        response.setMsg("Not Found Error");
        response.setData(data);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
    
    //Internal Server Error
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        Map<String, String> data = new HashMap<>();
        data.put("error", ex.getMessage());

        ResponseModel response = new ResponseModel();
        response.setMsg("Internal Server Error");
        response.setData(data);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
