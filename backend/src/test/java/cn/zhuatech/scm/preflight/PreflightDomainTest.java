/* 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.scm.preflight;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * 多供应商配额按物料分组的业务回归。上海如静知华信息科技有限公司 · https://www.zhuatech.cn/
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
class PreflightDomainTest {
  private final CsvPreflight service = new CsvPreflight();
  private final String header = "allocation_no,material,supplier,share,start_date,end_date\n";

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  @Test
  void multipleSuppliersSumToOneHundredExactly() {
    var r =
        service.preview(
            header
                + "A-1,M-1,S-1,60.25,2026-11-01,2027-12-31\n"
                + "A-2,M-1,S-2,39.75,2026-11-01,2027-12-31",
            "2026-10-23");
    assertEquals(true, r.get("valid"));
    assertEquals(2, r.get("validRows"));
  }

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  @Test
  void oneCentPercentageShortageMarksEntireGroup() {
    var r =
        service.preview(
            header
                + "A-1,M-1,S-1,60.24,2026-11-01,2027-12-31\n"
                + "A-2,M-1,S-2,39.75,2026-11-01,2027-12-31",
            "2026-10-23");
    assertEquals(false, r.get("valid"));
    assertEquals(2, r.get("invalidRows"));
  }

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  @Test
  void differentMaterialsDoNotMixQuotaTotals() {
    var r =
        service.preview(
            header + "A-1,M-1,S-1,100,2026-11-01,2027-12-31\nA-2,M-2,S-1,100,2026-11-01,2027-12-31",
            "2026-10-23");
    assertEquals(true, r.get("valid"));
    assertEquals(2, r.get("validRows"));
  }
}
