package collections.solutions;

import java.util.HashMap;
import java.util.Map;

/**
 * Exercise 2.3 — Enum Lookup Map (HttpStatus)
 *
 * Demonstrates a static lookup Map inside an enum,
 * a fromCode() factory method, and exception handling.
 */
public class HttpStatusDemo {

    enum HttpStatus {
        OK(200, "OK"),
        CREATED(201, "Created"),
        BAD_REQUEST(400, "Bad Request"),
        UNAUTHORIZED(401, "Unauthorized"),
        NOT_FOUND(404, "Not Found"),
        INTERNAL_SERVER_ERROR(500, "Internal Server Error");

        private final int code;
        private final String message;

        // Static lookup map: code -> HttpStatus
        private static final Map<Integer, HttpStatus> CODE_MAP = new HashMap<>();

        static {
            for (HttpStatus status : values()) {
                CODE_MAP.put(status.code, status);
            }
        }

        HttpStatus(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

        /**
         * Looks up an HttpStatus by its numeric code.
         *
         * @throws IllegalArgumentException if the code is unknown
         */
        public static HttpStatus fromCode(int code) {
            HttpStatus status = CODE_MAP.get(code);
            if (status == null) {
                throw new IllegalArgumentException("Unknown HTTP status code: " + code);
            }
            return status;
        }

        @Override
        public String toString() {
            return code + " " + message;
        }
    }

    public static void main(String[] args) {

        // Look up valid codes
        int[] codes = { 200, 404, 999 };

        for (int code : codes) {
            try {
                HttpStatus status = HttpStatus.fromCode(code);
                System.out.println("Code " + code + " -> " + status);
            } catch (IllegalArgumentException e) {
                System.out.println("Code " + code + " -> " + e.getMessage());
            }
        }
        // Output:
        // Code 200 -> 200 OK
        // Code 404 -> 404 Not Found
        // Code 999 -> Unknown HTTP status code: 999
    }
}
