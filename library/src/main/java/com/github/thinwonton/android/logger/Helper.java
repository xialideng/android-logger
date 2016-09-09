package com.github.thinwonton.android.logger;

final class Helper {

	private Helper() {
	}

    /**
     * [thread][(ClassName.java:lineNumber)#Method]
     */
    private final String CALLER_MESSAGE_FORMATTER = "[%s][(%s.java:%s)#%s]";

	public static final String LINE_SEPARATOR = getLineSeparator();

	public static String getLineSeparator() {
		return System.getProperty("line.separator");
	}

	public static boolean isNull(Object o) {
		return o == null;
	}

	public static boolean isEmpty(CharSequence str) {
		return str == null || str.length() == 0;
	}

    public static String makeCallerInfos(String[] caller) {
        return String.format(CALLER_MESSAGE_FORMATTER, caller[0], caller[1], caller[2], caller[3]);
    }

    public static String makePrintStack(String caller, String message) {
        StringBuffer sb = new StringBuffer();
        return sb.append(caller).append("  ").append(message).toString();
    }

	public static boolean equals(CharSequence a, CharSequence b) {
		if (a == b)
			return true;
		if (a != null && b != null) {
			int length = a.length();
			if (length == b.length()) {
				if (a instanceof String && b instanceof String) {
					return a.equals(b);
				} else {
					for (int i = 0; i < length; i++) {
						if (a.charAt(i) != b.charAt(i))
							return false;
					}
					return true;
				}
			}
		}
		return false;
	}

}
