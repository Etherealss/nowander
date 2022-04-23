package com.nowander.basesystem.user.security.anonymous.annotation;

import com.nowander.basesystem.user.security.anonymous.RequestMethodEnum;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.pattern.PathPattern;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wtk
 * @date 2022-04-23
 */
public class AnonymousUrlUtil {
    public static Map<String, Set<String>> getAnonymousUrl(RequestMappingHandlerMapping handlerMapping) {
        // 允许匿名访问的URL集合
        Map<String, Set<String>> anonymousUrls = new HashMap<>(6);
        // 各种HTTP请求方式对应的URL集合
        Set<String> get = new HashSet<>();
        Set<String> post = new HashSet<>();
        Set<String> put = new HashSet<>();
        Set<String> delete = new HashSet<>();
        Set<String> patch = new HashSet<>();
        Set<String> all = new HashSet<>();
        // 从handlerMapping中获取所有Controller的Method，过滤出加了@AnonymousAccess注解的接口
        List<Map.Entry<RequestMappingInfo, HandlerMethod>> entries = handlerMapping.getHandlerMethods().entrySet()
                .stream()
                .filter(entry -> entry.getValue().getMethodAnnotation(AnonymousAccess.class) != null)
                .collect(Collectors.toList());
        // 遍历添加了@AnonymousAccess注解的接口，获取其配置的URL，并根据HTTP请求方式添加到对应的Map中
        for (Map.Entry<RequestMappingInfo, HandlerMethod> infoEntry : entries) {
            List<RequestMethod> requestMethods = new ArrayList<>(infoEntry.getKey().getMethodsCondition().getMethods());
            RequestMethodEnum requestMethod = RequestMethodEnum.find(
                    requestMethods.size() == 0 ?
                            RequestMethodEnum.ALL.getType() : requestMethods.get(0).name()
            );
            // 能够访问该方法的URL，一般只有一条URL
            Set<String> urls = new HashSet<>(2);
            if (infoEntry.getKey().getPatternsCondition() != null) {
                // 不包含路径变量的URL
                urls.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
            }
            if (infoEntry.getKey().getPathPatternsCondition() != null) {
                // 包含了路径变量的URL，需要将 abc/{xxx}/xyz 替换成 abc/*/xyz
                urls.addAll(infoEntry.getKey()
                        .getPathPatternsCondition()
                        .getPatterns()
                        .stream()
                        .map(PathPattern::getPatternString)
                        .map(s -> s.replaceAll("\\{\\w++\\}", "*"))
                        .collect(Collectors.toSet())
                );
            }
            // 根据HTTP请求方式添加到对应的Map中
            switch (Objects.requireNonNull(requestMethod)) {
                case GET:       get.addAll(urls);       break;
                case POST:      post.addAll(urls);      break;
                case PUT:       put.addAll(urls);       break;
                case DELETE:    delete.addAll(urls);    break;
                case PATCH:     patch.addAll(urls);     break;
                default:        all.addAll(urls);       break;
            }
        }

        anonymousUrls.put(RequestMethodEnum.GET.getType(), get);
        anonymousUrls.put(RequestMethodEnum.POST.getType(), post);
        anonymousUrls.put(RequestMethodEnum.PUT.getType(), put);
        anonymousUrls.put(RequestMethodEnum.DELETE.getType(), delete);
        anonymousUrls.put(RequestMethodEnum.PATCH.getType(), patch);
        anonymousUrls.put(RequestMethodEnum.ALL.getType(), all);
        return anonymousUrls;
    }
}
