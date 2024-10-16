package vn.hoangkhang.laptopshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import vn.hoangkhang.laptopshop.domain.OrderDetailMongo;
import vn.hoangkhang.laptopshop.domain.OrderMongo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.NumberFormat;
import java.util.Locale;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendOrderConfirmationEmail(String toEmail, OrderMongo order) throws MessagingException {

        String htmlContent = buildHtmlTemplate(order);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(toEmail);
        helper.setSubject("Xác nhận đơn hàng");
        helper.setText(htmlContent, true);

        mailSender.send(message);
    }

    public String buildHtmlTemplate(OrderMongo order) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        @SuppressWarnings("deprecation")
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

        String htmlContent = "<html dir=\"ltr\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\r\n"
                + //
                "  <head>\r\n" + //
                "    <meta charset=\"UTF-8\">\r\n" + //
                "    <meta content=\"width=device-width, initial-scale=1\" name=\"viewport\">\r\n" + //
                "    <meta name=\"x-apple-disable-message-reformatting\">\r\n" + //
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n" + //
                "    <meta content=\"telephone=no\" name=\"format-detection\">\r\n" + //
                "    <title></title>\r\n" + //
                "  </head>\r\n" + //
                "  <body class=\"body\">\r\n" + //
                "    <div dir=\"ltr\" class=\"es-wrapper-color\">\r\n" + //
                "      <!--[if gte mso 9]>\r\n" + //
                "\t\t\t<v:background xmlns:v=\"urn:schemas-microsoft-com:vml\" fill=\"t\">\r\n" + //
                "\t\t\t\t<v:fill type=\"tile\" color=\"#f6f6f6\"></v:fill>\r\n" + //
                "\t\t\t</v:background>\r\n" + //
                "\t\t<![endif]-->\r\n" + //
                "      <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" class=\"es-wrapper\">\r\n" + //
                "        <tbody>\r\n" + //
                "          <tr>\r\n" + //
                "            <td valign=\"top\" class=\"esd-email-paddings\">\r\n" + //
                "              <table cellpadding=\"0\" cellspacing=\"0\" align=\"center\" class=\"es-content\">\r\n" + //
                "                <tbody>\r\n" + //
                "                  <tr>\r\n" + //
                "                    <td align=\"center\" class=\"esd-stripe\">\r\n" + //
                "                      <table bgcolor=\"#ffffff\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" esd-img-prev-src class=\"es-content-body\">\r\n"
                + //
                "                        <tbody>\r\n" + //
                "                          <tr>\r\n" + //
                "                            <td align=\"left\" esd-img-prev-src esd-img-prev-position=\"center top\" class=\"esd-structure es-p20t es-p20r es-p20l\" style=\"background-position:center top\">\r\n"
                + //
                "                              <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\r\n" + //
                "                                <tbody>\r\n" + //
                "                                  <tr>\r\n" + //
                "                                    <td width=\"560\" align=\"center\" valign=\"top\" class=\"esd-container-frame\">\r\n"
                + //
                "                                      <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\r\n" + //
                "                                        <tbody>\r\n" + //
                "                                          <tr>\r\n" + //
                "                                            <td align=\"center\" class=\"esd-block-text\">\r\n" + //
                "                                              <h2 style=\"color:#659c35\">\r\n" + //
                "                                                Cảm ơn bạn đã mua hàng của chúng tôi!\r\n" + //
                "                                              </h2>\r\n" + //
                "                                            </td>\r\n" + //
                "                                          </tr>\r\n" + //
                "                                        </tbody>\r\n" + //
                "                                      </table>\r\n" + //
                "                                    </td>\r\n" + //
                "                                  </tr>\r\n" + //
                "                                </tbody>\r\n" + //
                "                              </table>\r\n" + //
                "                            </td>\r\n" + //
                "                          </tr>\r\n" + //
                "                          <tr>\r\n" + //
                "                            <td align=\"left\" esd-img-prev-src esd-img-prev-position=\"center top\" class=\"esd-structure es-p20t es-p10b es-p20r es-p20l\" style=\"background-position:center top\">\r\n"
                + //
                "                              <!--[if mso]><table width=\"560\" cellpadding=\"0\" cellspacing=\"0\"><tr><td width=\"280\" valign=\"top\"><![endif]-->\r\n"
                + //
                "                              <table cellspacing=\"0\" cellpadding=\"0\" align=\"left\" class=\"es-left\">\r\n"
                + //
                "                                <tbody>\r\n" + //
                "                                  <tr>\r\n" + //
                "                                    <td width=\"280\" align=\"left\" class=\"es-m-p20b esd-container-frame\">\r\n"
                + //
                "                                      <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#efefef\" esd-img-prev-src esd-img-prev-position=\"center top\" style=\"background-color:#efefef;border-collapse:separate;background-position:center top;border-width:1px 0 1px 1px;border-style:solid solid solid solid;border-color:rgba(0,0,0,0) rgba(0,0,0,0) rgba(0,0,0,0) rgba(0,0,0,0)\">\r\n"
                + //
                "                                        <tbody>\r\n" + //
                "                                          <tr>\r\n" + //
                "                                            <td align=\"left\" class=\"esd-block-text es-p20t es-p10b es-p20r es-p20l\">\r\n"
                + //
                "                                              <h4 style=\"color:#659c35\">\r\n" + //
                "                                                Tóm tắt:\r\n" + //
                "                                              </h4>\r\n" + //
                "                                            </td>\r\n" + //
                "                                          </tr>\r\n" + //
                "                                          <tr>\r\n" + //
                "                                            <td align=\"left\" class=\"esd-block-text es-p20b es-p20r es-p20l\">\r\n"
                + //
                "                                              <table cellspacing=\"1\" cellpadding=\"1\" border=\"0\" align=\"left\" class=\"cke_show_border\" style=\"width:100%\">\r\n"
                + //
                "                                                <tbody>\r\n" + //
                "                                                  <tr>\r\n" + //
                "                                                    <td style=\"font-size:14px;line-height:150%\">\r\n"
                + //
                "                                                      Mã đơn hàng:\r\n" + //
                "                                                    </td>\r\n" + //
                "                                                    <td>\r\n" + //
                "                                                      <strong><span style=\"font-size:14px;line-height:150%\">"
                + order.getId() + "</span></strong>\r\n"
                + //
                "                                                    </td>\r\n" + //
                "                                                  </tr>\r\n" + //
                "                                                  <tr>\r\n" + //
                "                                                    <td style=\"font-size:14px;line-height:150%\">\r\n"
                + //
                "                                                      Ngày đặt hàng:\r\n" + //
                "                                                    </td>\r\n" + //
                "                                                    <td>\r\n" + //
                "                                                      <strong><span style=\"font-size:14px;line-height:150%\">"
                + dateFormat.format(order.getOrderDate()) + "</span></strong>\r\n"
                + //
                "                                                    </td>\r\n" + //
                "                                                  </tr>\r\n" + //
                "                                                  <tr>\r\n" + //
                "                                                    <td style=\"font-size:14px;line-height:150%\">\r\n"
                + //
                "                                                      Tổng đơn hàng:\r\n" + //
                "                                                    </td>\r\n" + //
                "                                                    <td>\r\n" + //
                "                                                      <strong><span style=\"font-size:14px;line-height:150%\">"
                + currencyFormatter.format(order.getTotalPrice()) + "</span></strong>\r\n"
                + //
                "                                                    </td>\r\n" + //
                "                                                  </tr>\r\n" + //
                "                                                </tbody>\r\n" + //
                "                                              </table>\r\n" + //
                "                                              <p style=\"line-height:150%\">\r\n" + //
                "                                                <br>\r\n" + //
                "                                              </p>\r\n" + //
                "                                            </td>\r\n" + //
                "                                          </tr>\r\n" + //
                "                                        </tbody>\r\n" + //
                "                                      </table>\r\n" + //
                "                                    </td>\r\n" + //
                "                                  </tr>\r\n" + //
                "                                </tbody>\r\n" + //
                "                              </table>\r\n" + //
                "                              <!--[if mso]></td><td width=\"0\"></td><td width=\"280\" valign=\"top\"><![endif]-->\r\n"
                + //
                "                              <table cellspacing=\"0\" cellpadding=\"0\" align=\"right\" class=\"es-right\">\r\n"
                + //
                "                                <tbody>\r\n" + //
                "                                  <tr>\r\n" + //
                "                                    <td width=\"280\" align=\"left\" class=\"esd-container-frame\">\r\n"
                + //
                "                                      <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#efefef\" esd-img-prev-src esd-img-prev-position=\"center top\" style=\"background-color:#efefef;border-collapse:separate;background-position:center top;border:1px solid rgba(0,0,0,0)\">\r\n"
                + //
                "                                        <tbody>\r\n" + //
                "                                          <tr>\r\n" + //
                "                                            <td align=\"left\" class=\"esd-block-text es-p20t es-p10b es-p20r es-p20l\">\r\n"
                + //
                "                                              <h4 style=\"color:#659c35\">\r\n" + //
                "                                                Thông tin giao hàng:\r\n" + //
                "                                              </h4>\r\n" + //
                "                                            </td>\r\n" + //
                "                                          </tr>\r\n" + //
                "                                          <tr>\r\n" + //
                "                                            <td align=\"left\" class=\"esd-block-text es-p20b es-p20r es-p20l\">\r\n"
                + //
                "                                              <p>\r\n" + //
                "                                                Người nhận: " + order.getReceiverName() + "\r\n" + //
                "                                              </p>\r\n" + //
                "                                              <p>\r\n" + //
                "                                                Địa chỉ: " + order.getReceiverAddress() + "\r\n" + //
                "                                              </p>\r\n" + //
                "                                              <p>\r\n" + //
                "                                                Số điện thoại: " + order.getReceiverPhone() + "\r\n" + //
                "                                              </p>\r\n" + //
                "                                            </td>\r\n" + //
                "                                          </tr>\r\n" + //
                "                                        </tbody>\r\n" + //
                "                                      </table>\r\n" + //
                "                                    </td>\r\n" + //
                "                                  </tr>\r\n" + //
                "                                </tbody>\r\n" + //
                "                              </table>\r\n" + //
                "                              <!--[if mso]></td></tr></table><![endif]-->\r\n" + //
                "                            </td>\r\n" + //
                "                          </tr>\r\n" + //
                "                        </tbody>\r\n" + //
                "                      </table>\r\n" + //
                "                    </td>\r\n" + //
                "                  </tr>\r\n" + //
                "                </tbody>\r\n" + //
                "              </table>\r\n" + //
                "              <table cellpadding=\"0\" cellspacing=\"0\" align=\"center\" class=\"es-content\">\r\n" + //
                "                <tbody>\r\n";

        for (OrderDetailMongo orderDetail : order.getOrderDetails()) {
            htmlContent += "                  <tr>\r\n" + //
                    "                    <td align=\"center\" class=\"esd-stripe\">\r\n" + //
                    "                      <table bgcolor=\"#ffffff\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" esd-img-prev-src class=\"es-content-body\">\r\n"
                    + //
                    "                        <tbody>\r\n" + //
                    "                          <tr>\r\n" + //
                    "                            <td align=\"left\" esd-img-prev-src esd-img-prev-position=\"center top\" class=\"esd-structure es-p10t es-p10b es-p20r es-p20l\" style=\"background-position:center top\">\r\n"
                    + //
                    "                              <!--[if mso]><table width=\"560\" cellpadding=\"0\" cellspacing=\"0\"><tr><td width=\"154\" valign=\"top\"><![endif]-->\r\n"
                    + //
                    "                              <table cellpadding=\"0\" cellspacing=\"0\" align=\"left\" class=\"es-left\">\r\n"
                    + //
                    "                                <tbody>\r\n" + //
                    "                                  <tr>\r\n" + //
                    "                                    <td width=\"154\" align=\"left\" class=\"es-m-p20b esd-container-frame\">\r\n"
                    + //
                    "                                      <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" esd-img-prev-src esd-img-prev-position=\"left top\" style=\"background-position:left top\">\r\n"
                    + //
                    "                                        <tbody>\r\n" + //
                    "                                          <tr>\r\n" + //
                    "                                            <td align=\"center\" class=\"esd-block-image\" style=\"font-size:0\">\r\n"
                    + //
                    "                                              <a target=\"_blank\" href=\"https://viewstripo.email/\">\r\n"
                    + //
                    "                                                <img src=\"/images/product/"
                    + orderDetail.getProduct().getImage()
                    + "\" alt=\"\" width=\"154\" class=\"adapt-img\" style=\"display:block\">\r\n"
                    + //
                    "                                              </a>\r\n" + //
                    "                                            </td>\r\n" + //
                    "                                          </tr>\r\n" + //
                    "                                        </tbody>\r\n" + //
                    "                                      </table>\r\n" + //
                    "                                    </td>\r\n" + //
                    "                                  </tr>\r\n" + //
                    "                                </tbody>\r\n" + //
                    "                              </table>\r\n" + //
                    "                              <!--[if mso]></td><td width=\"20\"></td><td width=\"386\" valign=\"top\"><![endif]-->\r\n"
                    + //
                    "                              <table cellpadding=\"0\" cellspacing=\"0\" align=\"right\" class=\"es-right\">\r\n"
                    + //
                    "                                <tbody>\r\n" + //
                    "                                  <tr>\r\n" + //
                    "                                    <td width=\"386\" align=\"left\" class=\"esd-container-frame\">\r\n"
                    + //
                    "                                      <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\r\n"
                    + //
                    "                                        <tbody>\r\n" + //
                    "                                          <tr>\r\n" + //
                    "                                            <td align=\"left\" class=\"esd-block-text es-p10t\">\r\n"
                    + //
                    "                                              <h3 class=\"es-m-txt-l\" style=\"color:#659c35;font-size:19px\">\r\n"
                    + //
                    "                                                <strong>" + orderDetail.getProduct().getName()
                    + "</strong>\r\n" + //
                    "                                              </h3>\r\n" + //
                    "                                            </td>\r\n" + //
                    "                                          </tr>\r\n" + //
                    "                                          <tr>\r\n" + //
                    "                                            <td align=\"left\" class=\"esd-block-text es-p10t\">\r\n"
                    + //
                    "                                              <h3 class=\"es-m-txt-l\" style=\"color:#659c35;font-size:19px\">\r\n"
                    + //
                    "                                                <strong><span style=\"color:#000000\">Số lượng:&nbsp;</span>"
                    + orderDetail.getQuantity() + "</strong>\r\n"
                    + //
                    "                                              </h3>\r\n" + //
                    "                                            </td>\r\n" + //
                    "                                          </tr>\r\n" + //
                    "                                          <tr>\r\n" + //
                    "                                            <td align=\"left\" class=\"esd-block-text es-p10t\">\r\n"
                    + //
                    "                                              <h3 class=\"es-m-txt-l\" style=\"color:#659c35;font-size:19px\">\r\n"
                    + //
                    "                                                <strong><span style=\"color:#000000\">Đơn giá:</span>&nbsp;"
                    + currencyFormatter.format(orderDetail.getPrice()) + "</strong>\r\n"
                    + //
                    "                                              </h3>\r\n" + //
                    "                                            </td>\r\n" + //
                    "                                          </tr>\r\n" + //
                    "                                        </tbody>\r\n" + //
                    "                                      </table>\r\n" + //
                    "                                    </td>\r\n" + //
                    "                                  </tr>\r\n" + //
                    "                                </tbody>\r\n" + //
                    "                              </table>\r\n" + //
                    "                              <!--[if mso]></td></tr></table><![endif]-->\r\n" + //
                    "                            </td>\r\n" + //
                    "                          </tr>\r\n" + //
                    "                        </tbody>\r\n" + //
                    "                      </table>\r\n" + //
                    "                    </td>\r\n" + //
                    "                  </tr>\r\n";
        }

        htmlContent += "                </tbody>\r\n" + //
                "              </table>\r\n" + //
                "            </td>\r\n" + //
                "          </tr>\r\n" + //
                "        </tbody>\r\n" + //
                "      </table>\r\n" + //
                "    </div>\r\n" + //
                "  </body>\r\n" + //
                "</html>";

        return htmlContent;
    }
}