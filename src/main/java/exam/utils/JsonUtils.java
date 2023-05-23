package exam.utils;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class JsonUtils {
    private static Gson gsonInstance;

    public static Gson getGsonInstance() {
        if (gsonInstance == null) {
            gsonInstance = new Gson();
        }
        return gsonInstance;
    }

    public static <T> List<T> readListFromString(String content, Class<T> clazz) {
        return getGsonInstance().fromJson(content, new ListOf<>(clazz));
    }

    private static class ListOf<T> implements ParameterizedType {
        private final Class<T> type;

        ListOf(Class<T> type) {
            this.type = type;
        }

        @Override
        public Type[] getActualTypeArguments() {
            return new Type[]{type};
        }

        @Override
        public Type getRawType() {
            return List.class;
        }

        @Override
        public Type getOwnerType() {
            return null;
        }
    }
}