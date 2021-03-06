package tr.com.dev.haliYikama.server.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.com.dev.haliYikama.server.utils.exeptions.DefaultExceptionAttributes;
import tr.com.dev.haliYikama.server.utils.exeptions.IExceptionAttributes;
import tr.com.dev.haliYikama.server.utils.interfaces.IBaseController;
import tr.com.dev.haliYikama.server.utils.interfaces.IGenericService;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by ramazancesur on 5/19/18.
 */
public abstract class BaseController<T extends BaseEntity>
        implements IBaseController<T> {

    /**
     * The Logger for this class.
     */
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IGenericService<T, Long> service;


    /**
     * Handles JPA NoResultExceptions thrown from web service controller
     * methods. Creates a response with Exception Attributes as JSON and HTTP
     * status code 404, not found.
     *
     * @param noResultException A NoResultException instance.
     * @param request           The HttpServletRequest in which the NoResultException was
     *                          raised.
     * @return A ResponseEntity containing the Exception Attributes in the body
     * and HTTP status code 404.
     */
    @ExceptionHandler(NoResultException.class)
    public ResponseEntity<Map<String, Object>> handleNoResultException(NoResultException noResultException,
                                                                       HttpServletRequest request) {

        logger.info("> handleNoResultException");

        IExceptionAttributes exceptionAttributes = new DefaultExceptionAttributes();

        Map<String, Object> responseBody = exceptionAttributes.getExceptionAttributes(noResultException, request,
                HttpStatus.NOT_FOUND);

        logger.info("< handleNoResultException");
        return new ResponseEntity<Map<String, Object>>(responseBody, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles all Exceptions not addressed by more specific
     * <code>@ExceptionHandler</code> methods. Creates a response with the
     * Exception Attributes in the response body as JSON and a HTTP status code
     * of 500, internal server error.
     *
     * @param exception An Exception instance.
     * @param request   The HttpServletRequest in which the Exception was raised.
     * @return A ResponseEntity containing the Exception Attributes in the body
     * and a HTTP status code 500.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception exception, HttpServletRequest request) {

        logger.error("> handleException");
        logger.error("- Exception: ", exception);

        IExceptionAttributes exceptionAttributes = new DefaultExceptionAttributes();

        Map<String, Object> responseBody = exceptionAttributes.getExceptionAttributes(exception, request,
                HttpStatus.INTERNAL_SERVER_ERROR);

        logger.error("< handleException");
        return new ResponseEntity<Map<String, Object>>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @Override
    @GetMapping("/getAll")
    public ResponseEntity<List<T>> getAll() {
        List<T> lstData = service.getAll();
        return new ResponseEntity<List<T>>(lstData, HttpStatus.OK);
    }

    @Override
    @GetMapping("/getData/{id}")
    public ResponseEntity<T> getDataById(@PathVariable("id") Long id) {
        T data = null;
        try {
            data = service.get(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<T>(data, HttpStatus.OK);
    }

    @Override
    @PutMapping
    public ResponseEntity<Boolean> addData(T data) {
        boolean result = service.add(data);
        if (!result) {
            return new ResponseEntity<Boolean>(result, HttpStatus.EXPECTATION_FAILED);
        }
        return new ResponseEntity<Boolean>(result, HttpStatus.OK);
    }

    @Override
    @PostMapping
    public ResponseEntity<Boolean> updateData(T data) {
        boolean result = service.update(data);
        if (!result) {
            return new ResponseEntity<Boolean>(result, HttpStatus.EXPECTATION_FAILED);
        }
        return new ResponseEntity<Boolean>(result, HttpStatus.OK);
    }

    @Override
    @DeleteMapping
    public ResponseEntity<Boolean> deleteData(T data) {
        boolean result = service.remove(data);
        if (!result) {
            return new ResponseEntity<Boolean>(result, HttpStatus.EXPECTATION_FAILED);
        }
        return new ResponseEntity<Boolean>(result, HttpStatus.OK);
    }
}
