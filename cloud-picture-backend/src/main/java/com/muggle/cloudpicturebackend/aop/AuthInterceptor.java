package com.muggle.cloudpicturebackend.aop;

import com.muggle.cloudpicturebackend.annotation.AuthCheck;
import com.muggle.cloudpicturebackend.exception.BusinessException;
import com.muggle.cloudpicturebackend.exception.ErrorCode;
import com.muggle.cloudpicturebackend.model.entity.User;
import com.muggle.cloudpicturebackend.model.enums.UserRoleEnum;
import com.muggle.cloudpicturebackend.service.UserService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 权限拦截器类，基于 AOP 实现对带有 @AuthCheck 注解的方法进行权限校验。
 * 该类作为切面类，在目标方法执行前后进行权限检查，若不满足权限要求则抛出业务异常，否则放行方法执行。
 */
@Aspect
@Component
public class AuthInterceptor {

    /**
     * 用户服务类，用于获取当前登录用户的信息。
     */
    @Resource
    private UserService userService;

    /**
     * 执行拦截逻辑，对带有 @AuthCheck 注解的方法进行权限校验。
     * 此方法为环绕通知，会在目标方法执行前后执行自定义逻辑，根据注解中指定的角色要求和当前用户的角色进行权限判断。
     *
     * @param joinPoint 切入点，用于获取目标方法的信息，并可控制目标方法的执行。
     * @param authCheck 权限校验注解实例，从中获取必须的角色信息。
     * @return 目标方法的执行结果。
     * @throws Throwable 当执行过程中出现异常时抛出。
     */
    @Around("@annotation(authCheck)")
    public Object doInterceptor(ProceedingJoinPoint joinPoint, AuthCheck authCheck) throws Throwable {
        // 从 @AuthCheck 注解中获取必须的角色
        String mustRole = authCheck.mustRole();

        // 获取当前请求的属性信息
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        // 从请求属性中获取 HttpServletRequest 对象
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

        // 通过用户服务类获取当前登录用户的信息
        User loginUser = userService.getLoginUser(request);

        // 将注解中指定的角色转换为 UserRoleEnum 枚举类型
        UserRoleEnum mustRoleEnum = UserRoleEnum.getEnumByValue(mustRole);

        // 如果注解中指定的角色为 null，说明不需要特定权限，直接放行目标方法
        if (mustRoleEnum == null) {
            return joinPoint.proceed();
        }

        // 将当前用户的角色转换为 UserRoleEnum 枚举类型
        UserRoleEnum userRoleEnum = UserRoleEnum.getEnumByValue(loginUser.getUserRole());

        // 如果当前用户的角色无法转换为有效的枚举类型，说明用户角色异常，抛出无权限异常
        if (userRoleEnum == null) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }

        // 如果注解要求必须是管理员角色，但当前用户不是管理员角色，抛出无权限异常
        if (UserRoleEnum.ADMIN.equals(mustRoleEnum) && !UserRoleEnum.ADMIN.equals(userRoleEnum)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }

        // 通过权限校验，继续执行目标方法并返回执行结果
        return joinPoint.proceed();
    }
}