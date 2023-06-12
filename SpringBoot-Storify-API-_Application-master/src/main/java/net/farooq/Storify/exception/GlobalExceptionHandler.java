package net.farooq.Storify.exception;

import net.farooq.Storify.playload.BugDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(StorifyBlogApiException.class)

    public ResponseEntity<BugDetails> handleBlogApiException(
            StorifyBlogApiException exception,
            WebRequest webRequest){
        BugDetails errorDetails = new BugDetails(
                new Date(),
                exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataNotFoundedException.class)

    public ResponseEntity<BugDetails> handleResourceNotFoundException(
            DataNotFoundedException exception,
            WebRequest webRequest) {
        BugDetails errorDetails = new BugDetails(
                new Date(),
                exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity(errorDetails,HttpStatus.NOT_FOUND);



    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BugDetails> handleGlobalException(
            Exception  exception, WebRequest webRequest
    ){
        BugDetails errorDetails=new BugDetails(
                new Date(),
                exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
