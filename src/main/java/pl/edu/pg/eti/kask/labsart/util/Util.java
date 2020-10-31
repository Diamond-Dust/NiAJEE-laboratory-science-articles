package pl.edu.pg.eti.kask.labsart.util;

import pl.edu.pg.eti.kask.labsart.avatar.servlet.AvatarServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
    public static URL createUrl(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException error) {
            throw new IllegalArgumentException(error.getMessage(), error);
        }
    }

    public static String parseRequestPath(HttpServletRequest request) {
        String path = request.getPathInfo();
        path = path != null ? path : "";
        return path;
    }

    public static String hash(String text) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(text.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xFF & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static String getFirstGroupFromPath(String path, Pattern pattern) {
        Matcher matcher = pattern.matcher(path);
        if (matcher.find()) {
            path = matcher.group().replaceAll("/", "");
        } else {
            path = "";
        }

        return path;
    }

    public static String getLoginFromPath(HttpServletRequest request, Pattern pattern) {
        //Parsed request path is valid with character pattern and can contain starting and ending '/'.
        return Util.getFirstGroupFromPath(
            Util.parseRequestPath(request),
            pattern
        );
    }

    public static InputStream getInputStreamFromRequest(HttpServletRequest request, String partName) throws IOException, ServletException {
        Part part = request.getPart(partName);

        if (part != null) {
            return part.getInputStream();
        } else if (request.getContentLength() > 0) {
            return request.getInputStream();
        } else {
            return null;
        }
    }

}
