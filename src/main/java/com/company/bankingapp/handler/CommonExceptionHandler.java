package com.company.bankingapp.handler;

import com.company.bankingapp.exception.NotAcceptableTokenException;
import com.company.bankingapp.exception.ResourceExistsException;
import com.company.bankingapp.exception.ResourceNotFoundException;
import com.company.bankingapp.exception.TokenRefreshException;
import com.company.bankingapp.model.response.ApiErrorResponse;
import com.company.bankingapp.model.response.ApiValidationErrorResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler extends ResponseEntityExceptionHandler {

    @NonNull
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatus status,
                                                                  @NonNull WebRequest request) {
        HttpServletRequest servletRequest = ((ServletWebRequest) request).getRequest();

        List<ApiValidationErrorResponse> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new ApiValidationErrorResponse(
                        fieldError.getField(),
                        fieldError.getRejectedValue(),
                        fieldError.getDefaultMessage()))
                .collect(Collectors.toList());

        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(ex.getMessage(), status, errors, servletRequest.getRequestURI());
        return buildResponseEntity(apiErrorResponse);
    }

    @NonNull
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body,
                                                             @NonNull HttpHeaders headers,
                                                             @NonNull HttpStatus status,
                                                             @NonNull WebRequest request) {
        HttpServletRequest servletRequest = ((ServletWebRequest) request).getRequest();
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(ex.getMessage(), status, servletRequest.getRequestURI());
        return buildResponseEntity(apiErrorResponse);
    }

    @NonNull
    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex,
                                                               @NonNull HttpHeaders headers,
                                                               @NonNull HttpStatus status,
                                                               @NonNull WebRequest request) {
        HttpServletRequest servletRequest = ((ServletWebRequest) request).getRequest();
        final var message = String.format("%s: path parameter is missing", ex.getVariableName());
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(message, status, servletRequest.getRequestURI());
        return buildResponseEntity(apiErrorResponse);
    }

    @NonNull
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                          @NonNull HttpHeaders headers,
                                                                          @NonNull HttpStatus status,
                                                                          @NonNull WebRequest request) {
        HttpServletRequest servletRequest = ((ServletWebRequest) request).getRequest();
        final var message = String.format("%s: request parameter is missing", ex.getParameterName());
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(message, status, servletRequest.getRequestURI());
        return buildResponseEntity(apiErrorResponse);
    }

    @NonNull
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex,
                                                                     @NonNull HttpHeaders headers,
                                                                     @NonNull HttpStatus status,
                                                                     @NonNull WebRequest request) {
        HttpServletRequest servletRequest = ((ServletWebRequest) request).getRequest();
        final var message = String.format("%s: request part is missing", ex.getRequestPartName());
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(message, status, servletRequest.getRequestURI());
        return buildResponseEntity(apiErrorResponse);
    }

    @NonNull
    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex,
                                                         @NonNull HttpHeaders headers,
                                                         @NonNull HttpStatus status,
                                                         @NonNull WebRequest request) {
        HttpServletRequest servletRequest = ((ServletWebRequest) request).getRequest();
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(ex.getMessage(), status, servletRequest.getRequestURI());
        return buildResponseEntity(apiErrorResponse);
    }

    @NonNull
    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex,
                                                        @NonNull HttpHeaders headers,
                                                        @NonNull HttpStatus status,
                                                        @NonNull WebRequest request) {
        ApiValidationErrorResponse fieldError = new ApiValidationErrorResponse(ex.getPropertyName(), ex.getValue(), ex.getMessage());
        HttpServletRequest servletRequest = ((ServletWebRequest) request).getRequest();
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(ex.getMessage(), status, List.of(fieldError), servletRequest.getRequestURI());
        return buildResponseEntity(apiErrorResponse);
    }

    @Override
    protected ResponseEntity<Object> handleAsyncRequestTimeoutException(AsyncRequestTimeoutException ex,
                                                                        @NonNull HttpHeaders headers,
                                                                        @NonNull HttpStatus status,
                                                                        @NonNull WebRequest request) {
        HttpServletRequest servletRequest = ((ServletWebRequest) request).getRequest();
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(ex.getMessage(), status, servletRequest.getRequestURI());
        return buildResponseEntity(apiErrorResponse);
    }

    @NonNull
    @Override
    protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatus status,
                                                                  @NonNull WebRequest request) {
        HttpServletRequest servletRequest = ((ServletWebRequest) request).getRequest();
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(ex.getMessage(), status, servletRequest.getRequestURI());
        return buildResponseEntity(apiErrorResponse);
    }

    @NonNull
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
                                                                      @NonNull HttpHeaders headers,
                                                                      @NonNull HttpStatus status,
                                                                      @NonNull WebRequest request) {
        HttpServletRequest servletRequest = ((ServletWebRequest) request).getRequest();
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(ex.getMessage(), status, servletRequest.getRequestURI());
        return buildResponseEntity(apiErrorResponse);
    }

    @NonNull
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
                                                                     @NonNull HttpHeaders headers,
                                                                     @NonNull HttpStatus status,
                                                                     @NonNull WebRequest request) {
        HttpServletRequest servletRequest = ((ServletWebRequest) request).getRequest();
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(ex.getMessage(), status, servletRequest.getRequestURI());
        return buildResponseEntity(apiErrorResponse);
    }

    @NonNull
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatus status,
                                                                  @NonNull WebRequest request) {
        HttpServletRequest servletRequest = ((ServletWebRequest) request).getRequest();
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(ex.getMessage(), status, servletRequest.getRequestURI());
        return buildResponseEntity(apiErrorResponse);
    }

    @NonNull
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatus status,
                                                                  @NonNull WebRequest request) {
        HttpServletRequest servletRequest = ((ServletWebRequest) request).getRequest();
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(ex.getMessage(), status, servletRequest.getRequestURI());
        return buildResponseEntity(apiErrorResponse);
    }

    @NonNull
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                                         @NonNull HttpHeaders headers,
                                                                         @NonNull HttpStatus status,
                                                                         @NonNull WebRequest request) {
        HttpServletRequest servletRequest = ((ServletWebRequest) request).getRequest();
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(ex.getMessage(), status, servletRequest.getRequestURI());
        return buildResponseEntity(apiErrorResponse);
    }

    @NonNull
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex,
                                                                   @NonNull HttpHeaders headers,
                                                                   @NonNull HttpStatus status,
                                                                   @NonNull WebRequest request) {
        HttpServletRequest servletRequest = ((ServletWebRequest) request).getRequest();
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(ex.getMessage(), status, servletRequest.getRequestURI());
        return buildResponseEntity(apiErrorResponse);
    }

    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex,
                                                                          @NonNull HttpHeaders headers,
                                                                          @NonNull HttpStatus status,
                                                                          @NonNull WebRequest request) {
        HttpServletRequest servletRequest = ((ServletWebRequest) request).getRequest();
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(ex.getMessage(), status, servletRequest.getRequestURI());
        return buildResponseEntity(apiErrorResponse);
    }

    @ExceptionHandler(ResourceExistsException.class)
    protected ResponseEntity<Object> handleResourceExistsException(ResourceExistsException ex, HttpServletRequest request) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(ex.getMessage(), HttpStatus.CONFLICT, request.getRequestURI());
        return buildResponseEntity(apiErrorResponse);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND, request.getRequestURI());
        return buildResponseEntity(apiErrorResponse);
    }

    @ExceptionHandler(NotAcceptableTokenException.class)
    protected ResponseEntity<Object> handleNotAcceptableTokenException(NotAcceptableTokenException ex, HttpServletRequest request) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED, request.getRequestURI());
        return buildResponseEntity(apiErrorResponse);
    }

    @ExceptionHandler(AuthenticationException.class)
    protected ResponseEntity<Object> handleAuthenticationException(AuthenticationException ex, HttpServletRequest request) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED, request.getRequestURI());
        return buildResponseEntity(apiErrorResponse);
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(ex.getMessage(), HttpStatus.FORBIDDEN, request.getRequestURI());
        return buildResponseEntity(apiErrorResponse);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    protected ResponseEntity<Object> handleUsernameNotFoundException(UsernameNotFoundException ex, HttpServletRequest request) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED, request.getRequestURI());
        return buildResponseEntity(apiErrorResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, request.getRequestURI());
        return buildResponseEntity(apiErrorResponse);
    }

    @ExceptionHandler(TokenRefreshException.class)
    protected ResponseEntity<Object> handleTokenRefreshException(TokenRefreshException ex, HttpServletRequest request) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED, request.getRequestURI());
        return buildResponseEntity(apiErrorResponse);
    }

    @ExceptionHandler(DisabledException.class)
    protected ResponseEntity<Object> handleDisabledException(DisabledException ex, HttpServletRequest request) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED, request.getRequestURI());
        return buildResponseEntity(apiErrorResponse);
    }

    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex, HttpServletRequest request) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED, request.getRequestURI());
        return buildResponseEntity(apiErrorResponse);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiErrorResponse apiErrorResponse) {
        return new ResponseEntity<>(apiErrorResponse, apiErrorResponse.status());
    }
}
