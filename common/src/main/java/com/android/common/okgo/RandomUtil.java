package com.android.common.okgo;

import com.android.common.util.LogUtils;

import java.security.SecureRandom;

/**
 * Created by grove on 2016/4/25.
 */
public class RandomUtil {

    private static final SecureRandom sr = new SecureRandom();

    /**
     * 产生任意给定长度的随纯机数字字符串
     *
     * @param iLength
     *            产生随机数的位数
     * @return
     */
    public static String getRandomIntNum(int iLength)
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < iLength; i++)
        {
            sb.append(Math.abs(sr.nextInt(10)));
        }
        return sb.toString();
    }

    /**
     * 生成给定长度的字符
     *
     * @param iLength
     *            产生随机字符的位数
     * @return
     */
    public static char[] getRandomCharArray(int iLength)
    {
        char[] ca = new char[iLength];
        for (int i = 0; i < ca.length; i++)
        {
            ca[i] = (char) (((Math.abs(sr.nextInt())) % 26) + (sr.nextBoolean() ? 65
                    : 97));
            // 65;//(byte)(Math.abs(ba[i]) % 72);
        }
        return ca;
    }

    /**
     * get a iLength random String
     *
     * @param iLength
     *            产生随机字符串的位数
     * @@desc 不限定字符的大小写
     * @return
     */
    public static String getRandomString(int iLength)
    {
        return new String(getRandomCharArray(iLength));
    }

    /**
     * 产生给定长度的字符串，可以制定大小写
     *
     * @param iLength
     * @param blToUpperCase
     *            ---true UpperCase, false LowCase
     * @return
     */
    public static String getRandomString(int iLength, boolean blToUpperCase)
    {

        return true == blToUpperCase ? getRandomString(iLength).toUpperCase()
                : getRandomString(iLength).toLowerCase();
    }

    /**
     * generate a Random int number which between 0 to {maxValue}
     *
     * @param maxValue
     * @return
     */
    public static int getRandomInt(int maxValue)
    {
        return sr.nextInt(maxValue);
    }

    /**
     * 根据位数生成一个10的倍数的整数
     *
     * @param len
     *            不要大于 最大整数范围的位数 8
     * @return
     */
    private static int genNumFromString(int len)
    {
        /*
         * if(len>10) { //TODO }
         */
        if (0 == len)
        {
            return 0;
        }
        StringBuffer sb = new StringBuffer("1");
        for (int i = 0; i < len; i++)
        {
            sb.append("0");
        }
        return Integer.parseInt(sb.toString());
    }

    /**
     * 得到要产生随机数的范围。 辅助使用位数生成实际随机数的方法
     *
     * @@TODO 这种方法，使得没有办法产生超过8位的随机数。要生成不限定整数个数的参看getRandomIntNum(int iLength)
     * @param aNum
     *            要生成随即数的最大值.
     * @return
     */
    public static int[] getScopeOfInt(int aNum)
    {
        /*
         * if(Integer.MAX_VALUE         */
        int len = String.valueOf(aNum).length();
        int maxIntNumLen = 10;// max int=2147483647
        int min = 0;
        if (maxIntNumLen == len)
        {
            min = genNumFromString(maxIntNumLen - 1);
        } else if (len > 1 && len < maxIntNumLen)
        {

            min = genNumFromString(len - 2);
        }

        return new int[] { min, aNum };
    }

    /**
     * 生成要求位数的随机数字
     *
     * @param limit
     *            要求的位数
     * @param maxValue
     *            最大值
     * @return
     */
    public static int getRandomInt(int limit, int maxValue)
    {
        if (0 >= limit)
        {
            return maxValue;
        }
        String strTmp = String.valueOf(maxValue);
        int len = strTmp.length();
        int intTmp = getRandomInt(maxValue);
        int[] scope = getScopeOfInt(maxValue);
        if (limit == len)
        {
            return maxValue;/* (intTmp>=scope[0]&&intTmp<=scope[1]&&(limit==(String.valueOf(intTmp).length())))?intTmp:getRandomInt(limit,maxValue); */
        }

        return /* (intTmp>=scope[0]&&intTmp<=scope[1]) */isInScope(intTmp,
                scope[0], scope[1]) ? (new Integer(String.valueOf(intTmp)
                .substring(0, limit)).intValue()) : getRandomInt(limit,
                maxValue);
    }

    /**
     * 判断一个数是否在规定范围内
     *
     * @param i
     * @param min
     * @param max
     * @return
     */
    public static boolean isInScope(int i, int min, int max)
    {
        return i <= max && i >= min;
    }

    /**
     * 根据已经计算出的字符的下标，得出数字的下标
     *
     * @param totleLimit
     *            生成随机码的总位数
     * @param charIndex
     *            字符下标集合
     * @return
     */
    public static int[] getIndexInsertNum(int totleLimit, int[] charIndex)
    {
        return getPositionNoBody(new int[totleLimit], charIndex);
    }

    /**
     * 得到要插入字符的位置下标
     *
     * @param totleLimit
     *            要产生整个随机码的长度
     * @param charLimit
     *            随即码中包含字符的个数
     * @return
     */
    public static int[] getIndexInsertChar(int totleLimit, int charLimit)
    {
        // 要插入字符的下标集合
        int[] chIndex = new int[charLimit];
        // 赞存处，辅助过滤，存放已经生成的INDEX
        int[] tmp = new int[charLimit];
        // 以会生成的随机数之外的数填充
        for (int ii = 0; ii < tmp.length; ii++)
        {
            tmp[ii] = totleLimit;
        }

        if (totleLimit < charLimit)
        {
            LogUtils.e("ERROR", "Error::要包含数字位数大于整个随机字符串长度");
            // TODO 是否在外部处理
        }
        int[] scope = getScopeOfInt(totleLimit);

        int comm = totleLimit + 1;
        for (int j = 0, i = 0; i < charLimit;)
        {

            int genNo = getRandomInt(totleLimit);

            if (comm != genNo && isInScope(genNo, scope[0], scope[1]))
            {
                boolean blFlag = false;
                for (int t = 0; t < tmp.length; t++)
                {
                    if (tmp[t] == genNo)
                    {
                        blFlag = true;
                        break;
                    }
                }
                if (blFlag)
                {
                    continue;
                }
                comm = genNo;
                tmp[j] = comm;
                chIndex[j] = comm;
                if (j == charLimit - 1)
                {
                    break;
                }
                i++;
                j++;

            }
        }
        return chIndex;
    }

    /**
     * 需要指定字符大小写的可以订制方法
     * @param limit
     *            总长度限制
     * @param numLimit
     *            含数字长度限制
     * @return 大小写混合字符和数字 串
     */
    public static String genRandomCheckString(int limit, int numLimit)
    {
        /*
         * 生成符合要求的随机数字串
         */
        // String num= getRandomIntNum(numLimit);
        /*
         * 生成符合要求的随机字符串
         */
        // char[] c= getRandomCharArray(limit-numLimit);
        /*
         * 字符插入下标
         */
        int[] indexChar = getIndexInsertChar(limit, limit - numLimit);
        /*
         * 数字插入下标
         */
        // int[] indexInt = getIndexInsertNum(limit, indexChar);
        return new String(componseIntAndCharArray(new char[limit], indexChar,
                getIndexInsertNum(limit, indexChar), getRandomCharArray(limit
                        - numLimit), getRandomIntNum(numLimit).toCharArray()));
    }

    // 得到没有占位的下标
    public static int[] getPositionNoBody(int[] totle, int[] already)
    {
        int lenTotle = totle.length;
        int lenAlready = already.length;
        int[] noBodyPosition = new int[lenTotle - lenAlready];
        for (int index = 0, i = 0; i < lenTotle; i++)
        {
            boolean bFlag = true;
            for (int j = 0; j < lenAlready; j++)
            {
                if (i == already[j])
                {
                    bFlag = false;
                    break;
                }
            }
            if (bFlag && index < noBodyPosition.length)
            {
                noBodyPosition[index++] = i;

            }
        }
        return noBodyPosition;
    }

    /**
     * 合并到最终结果
     *
     * @param destA
     * @param chIndex
     * @param iIndex
     * @param chStore
     * @param iStore
     * @return
     */
    public static char[] componseIntAndCharArray(char[] destA, int[] chIndex,
                                                 int[] iIndex, char[] chStore, char[] iStore)
    {
        int lenDest = destA.length;
        int lenCh = chIndex.length;
        int leniA = iIndex.length;
        // 取小的数
        if (lenDest != (lenCh + leniA) || lenCh != chStore.length
                || leniA != iStore.length)
        {
            // TODO Error
            return null;
        }
        // start to work

        // dest {1,2,3}
        // ch {1} {b}
        // i{2,3} {8,9}

        for (int i = 0; i < lenDest; i++)
        {
            for (int j = 0; j < lenCh; j++)
            {

                if (i == chIndex[j])
                {
                    destA[i] = chStore[j];
                }

            }
            for (int j = 0; j < leniA; j++)
            {

                if (i == iIndex[j])
                {
                    destA[i] = iStore[j];
                }

            }
        }
        return destA;
    }


    private void testComponseIntAndCharArray()
    {
        char[] cA = new char[5];
        int[] iIndex = new int[] { 1, 4 };
        int[] cIndex = new int[] { 0, 2, 3 };
        char[] cStore = "abc".toCharArray();
        char[] iStore = "12".toCharArray();
        System.out.println(new String(componseIntAndCharArray(cA, cIndex,
                iIndex, cStore, iStore)));
    }

    private void test()
    {
        int[] tmp = getPositionNoBody(new int[15], new int[] { 13, 2, 3, 4, 5,
                6 });
        for (int i = 0; i < tmp.length; i++)
        {
            System.out.println(tmp[i]);
        }
    }

    private void testGetIndexInsertChar()
    {
        int[] tmp = getIndexInsertChar(9, 5);
        for (int i = 0; i < tmp.length; i++)
            System.out.print("\nchar::" + tmp[i]);
        int[] tmpOther = getIndexInsertNum(9, tmp);
        for (int i = 0; i < tmpOther.length; i++)
            System.out.print("\nnum::" + tmpOther[i]);
    }
}
