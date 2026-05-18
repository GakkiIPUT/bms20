/*
 * プロジェクト名：書籍管理システムWeb版Ver1.0
 * プログラム名：MyFormat.java
 * プログラムの説明：金額などの表示形式を変換するユーティリティクラス。
 * 作成日：2026年5月12日
 * 作成者：髙垣湧侑翔
*/

package util;

import java.text.DecimalFormat;

public class MyFormat {
	public String moneyFormat(int price) {

		DecimalFormat df = new DecimalFormat("\u00A5#,###");
		return df.format(price);

	}
}