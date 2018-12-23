package rest.v1.interceptors.annotations;
import javax.interceptor.InterceptorBinding;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@InterceptorBinding
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogContext {

}