package com.babyduncan.restful;

import com.babyduncan.pattern.Element;
import com.babyduncan.pattern.PathInput;
import com.babyduncan.pattern.PathPattern;
import com.babyduncan.pattern.StringElement;
import com.babyduncan.servlet.ServletAction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: guohaozhao (guohaozhao116008@sohu-inc.com)
 * Date: 11/28/13 18:17
 */
public class RestfulRegistryCenter {
    public static final Map<String, List<PathPattern<ServletAction>>> map = new HashMap<String, List<PathPattern<ServletAction>>>();

    public static void addRestfulMapping(String restfulPattern, String url) {
        ServletAction servletAction = new ServletAction(url, ServletAction.Type.REDIRECT);
        PathPattern<ServletAction> pattern = new PathPattern<ServletAction>(servletAction, restfulPattern, true, 3);
        String key = pattern.getKey();
        List<PathPattern<ServletAction>> list = new ArrayList<PathPattern<ServletAction>>();
        map.put(key, list);
        list.add(pattern);
    }

    public static String getRealUrl(String restfulURI, Map<String, String> params) {
        PathInput input = new PathInput(restfulURI, 1);
        String key___ = input.value();
        List<PathPattern<ServletAction>> list___ = map.get(key___);
        if (list___ != null) {
            for (PathPattern<ServletAction> pattern___ : list___) {
                if (pattern___.match(input)) {
                    List<String> paramsKeys = new ArrayList<String>();
                    List<String> paramsValues = new ArrayList<String>();
                    for (String s___ : input.items) {
                        paramsKeys.add(s___);
                    }
                    for (Element e : pattern___.elements) {
                        if (e instanceof StringElement) {
                            paramsValues.add(((StringElement) e).getProperties());
                        }
                    }
                    for (int i = 0; i < paramsValues.size(); i++) {
                        params.put(paramsValues.get(i), paramsKeys.get(i + 1));
                    }
                    return pattern___.getTarget().target;
                }
            }
        }
        return null;
    }
}
