package com.nowander.common.enums;

/**
 * @author wtk
 * @date 2022-01-30
 */
public class ScheduleConstants {


    public static final Long JOB_ID_SAVE_LIKE_RECORD = 1L;
    public static final Long JOB_ID_SAVE_LIKE_COUNT = 2L;


    public static final String JOB_JROUP_SAVE_LIKE = "saveLike";


    /** 执行目标key前缀 */
    public static final String TASK_PROPERTIES = "TASK_PROPERTIES";

    /** MISFIRE 默认 */
    public static final String MISFIRE_DEFAULT = "0";

    /** MISFIRE 立即触发执行 */
    public static final String MISFIRE_IGNORE_MISFIRES = "1";

    /** MISFIRE 触发一次执行 */
    public static final String MISFIRE_FIRE_AND_PROCEED = "2";

    /** MISFIRE 不触发立即执行 */
    public static final String MISFIRE_DO_NOTHING = "3";

    /**
     * 状态枚举
     */
    public enum Status {
        /**
         * 正常
         */
        NORMAL("0"),
        /**
         * 暂停
         */
        PAUSE("1");

        private final String value;

        private Status(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
