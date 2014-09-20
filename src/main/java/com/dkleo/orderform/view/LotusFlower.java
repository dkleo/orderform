package com.dkleo.orderform.view;

import com.dkleo.orderform.App;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.Image;
import java.text.DecimalFormat;
import java.util.Map;

/**
 * Created by Derek on 7/14/14.
 */
public class LotusFlower extends AbstractPdfView {

    @Override
    protected void buildPdfDocument(
            Map model,
            Document document,
            PdfWriter writer,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        DecimalFormat currencyFmt = new DecimalFormat("'$'#.##");
        currencyFmt.setMinimumFractionDigits(2);
        currencyFmt.setMaximumFractionDigits(2);

        DecimalFormat percFmt = new DecimalFormat("#.##'%'");
        percFmt.setMinimumFractionDigits(2);
        percFmt.setMaximumFractionDigits(2);




        Chunk space = new Chunk(" ");
        Phrase p1 = new Phrase(new Chunk("Blue Kite Press", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14) ));
        Phrase p2 = new Phrase(new Chunk("68 Maple Street", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14) ));
        Phrase p3 = new Phrase(new Chunk("Croton-on-Hudson, NY 10520", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14) ));
        Phrase p4 = new Phrase(new Chunk("917-340-6703", FontFactory.getFont(FontFactory.HELVETICA, 14) ));
        Chunk c5 = new Chunk("info@bluekitepress.com", FontFactory.getFont(FontFactory.HELVETICA, 14, Color.BLUE) );
        c5.setUnderline(0.2f, -2f);
        Anchor p5 = new Anchor(c5);
        p5.setReference("mailto:info@bluekitepress.com");

        Phrase p6 = new Phrase(new Chunk("ORDER FORM", FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE, 24) ));
        Phrase p7 = new Phrase(new Chunk("A Lotus Flower in Muddy Waters", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14))) ;
        Phrase p8 = new Phrase(new Chunk("(autographed copy of the book)", FontFactory.getFont(FontFactory.HELVETICA, 14))) ;

        Paragraph orderTo = new Paragraph();
        orderTo.setAlignment(Paragraph.ALIGN_CENTER);
        orderTo.add(p1);
        orderTo.add(Chunk.NEWLINE);
        orderTo.add(p2);
        orderTo.add(Chunk.NEWLINE);
        orderTo.add(p3);
        orderTo.add(Chunk.NEWLINE);
        orderTo.add(p4);
        orderTo.add(Chunk.NEWLINE);
        orderTo.add(p5);
        orderTo.add(Chunk.NEWLINE);
        orderTo.add(Chunk.NEWLINE);
        orderTo.add(p6);
        orderTo.add(Chunk.NEWLINE);
        orderTo.add(Chunk.NEWLINE);
        orderTo.add(Chunk.NEWLINE);
        orderTo.add(Chunk.NEWLINE);
        orderTo.add(Chunk.NEWLINE);
        orderTo.add(p7);
        orderTo.add(space);
        orderTo.add(p8);
        orderTo.add(Chunk.NEWLINE);
        orderTo.add(Chunk.NEWLINE);

        int Quantity = StringUtils.isEmpty(request.getParameter("quant")) ? 1 : Integer.parseInt(request.getParameter("quant"));
        logger.debug("Quantity:  " + Quantity);
        double Price = Quantity * 14.95;
        logger.debug("Price:  " + Price);
        double Shipping =  (request.getParameter("country").equals("United States") || request.getParameter("country").equals("Canada") ) ? calculateShipping (Quantity, true) : calculateShipping (Quantity, false);
        logger.debug("Shipping:  " + Shipping);
        double Tax = (request.getParameter("state").trim().toUpperCase().equals("NY")) ?  (0.08875 * (Price + Shipping))  : 0.00;

        logger.debug("Tax:  " + Tax);
        double Total = Price + Tax + Shipping;
        logger.debug("Total:  " + Total);



        Phrase p9 = new Phrase(new Chunk("Quantity:  ", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14) ));
        Chunk c10 = new Chunk(String.valueOf(Quantity), FontFactory.getFont(FontFactory.HELVETICA, 14) );
        c10.setUnderline(0.2f, -2f);
        Phrase p10 = new Phrase(c10);

        Phrase p11 = new Phrase(new Chunk("Price:  ", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14) ));
        Chunk c12 = new Chunk(currencyFmt.format(Price), FontFactory.getFont(FontFactory.HELVETICA, 14));
        c12.setUnderline(0.2f, -2f);
        Phrase p12 = new Phrase( c12 );
        Phrase p13 = new Phrase(new Chunk("     $14.95 USD each (for orders of 10 or more books please contact)", FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 10) ));

        Phrase p14 = new Phrase(new Chunk("Shipping:  ", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14) ));
        Chunk c15 = new Chunk(currencyFmt.format(Shipping), FontFactory.getFont(FontFactory.HELVETICA, 14));
        c15.setUnderline(0.2f, -2f);
        Phrase p15 = new Phrase( c15 );
        Phrase p16 = new Phrase(new Chunk("     $4.95 domestic ~ $12.00 int'l; 1/2 price for each add'l copy", FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 10) ));

        Phrase p17 = new Phrase(new Chunk("NYS Sales Tax:  ", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14) ));
        Chunk c18 = new Chunk(currencyFmt.format(Tax), FontFactory.getFont(FontFactory.HELVETICA, 14));
        c18.setUnderline(0.2f, -2f);
        Phrase p18 = new Phrase( c18 );
        Phrase p19 = new Phrase(new Chunk("     8.875 % (NY State Residents Only)", FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 10) ));

        Phrase p20 = new Phrase(new Chunk("Total:  ", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14) ));
        Chunk c21 = new Chunk(currencyFmt.format(Total), FontFactory.getFont(FontFactory.HELVETICA, 14) );
        c21.setUnderline(0.2f, -2f);
        Phrase p21 = new Phrase(c21);
        Phrase p22 = new Phrase(new Chunk("     (Check or Money Order)",FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 10)));


        Paragraph priceSummary = new Paragraph();
        priceSummary.add(p9);
        priceSummary.add(p10);
        priceSummary.add(Chunk.NEWLINE);

        priceSummary.add(p11);
        priceSummary.add(p12);
        priceSummary.add(p13);
        priceSummary.add(Chunk.NEWLINE);

        priceSummary.add(p14);
        priceSummary.add(p15);
        priceSummary.add(p16);
        priceSummary.add(Chunk.NEWLINE);

        priceSummary.add(p17);
        priceSummary.add(p18);
        priceSummary.add(p19);
        priceSummary.add(Chunk.NEWLINE);

        priceSummary.add(p20);
        priceSummary.add(p21);
        priceSummary.add(p22);
        priceSummary.add(Chunk.NEWLINE);
        priceSummary.add(Chunk.NEWLINE);


        Phrase p23 = new Phrase(new Chunk("DATE:  ", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14) ));
        Chunk c24 = new Chunk(App.now(), FontFactory.getFont(FontFactory.HELVETICA, 14) );
        c24.setUnderline(0.2f, -2f);
        Phrase p24 = new Phrase(c24);

        Phrase p25 = new Phrase(new Chunk("NAME:  ", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14) ));
        Chunk c26 = new Chunk(request.getParameter("cn"), FontFactory.getFont(FontFactory.HELVETICA, 14) );
        c26.setUnderline(0.2f, -2f);
        Phrase p26 = new Phrase(c26);

        Phrase p27 = new Phrase(new Chunk("ADDRESS:  ", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14) ));
        Chunk c28= new Chunk(request.getParameter("ady1"), FontFactory.getFont(FontFactory.HELVETICA, 14) );
        c28.setUnderline(0.2f, -2f);
        Phrase p28 = new Phrase(c28);

//        if(!StringUtils.isEmpty(request.getParameter("ady2"))) {
//            Phrase _p27 = new Phrase(new Chunk("ADDRESS:  ", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14) ));
//            Chunk _c28= new Chunk(request.getParameter("ady1"), FontFactory.getFont(FontFactory.HELVETICA, 14) );
//            _c28.setUnderline(0.2f, -2f);
//            Phrase _p28 = new Phrase(_c28);
//        }

        Phrase p29 = new Phrase(new Chunk("CITY:  ", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14) ));
        Chunk c30= new Chunk(request.getParameter("city"), FontFactory.getFont(FontFactory.HELVETICA, 14) );
        c30.setUnderline(0.2f, -2f);
        Phrase p30 = new Phrase(c30);

        Phrase p31 = new Phrase(new Chunk("STATE:  ", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14) ));
        Chunk c32= new Chunk(request.getParameter("state"), FontFactory.getFont(FontFactory.HELVETICA, 14) );
        c32.setUnderline(0.2f, -2f);
        Phrase p32 = new Phrase(c32);

        Phrase p33 = new Phrase(new Chunk("ZIP:  ", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14) ));
        Chunk c34= new Chunk(request.getParameter("zip"), FontFactory.getFont(FontFactory.HELVETICA, 14) );
        c34.setUnderline(0.2f, -2f);
        Phrase p34 = new Phrase(c34);

        Phrase p35 = new Phrase(new Chunk("COUNTRY:  ", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14) ));
        Chunk c36= new Chunk(request.getParameter("country"), FontFactory.getFont(FontFactory.HELVETICA, 14) );
        c36.setUnderline(0.2f, -2f);
        Phrase p36 = new Phrase(c36);

        //Phrase p37 = new Phrase(new Chunk("PROMO CODE:  ", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14) ));
        //Chunk c38= new Chunk(request.getParameter("pc"), FontFactory.getFont(FontFactory.HELVETICA, 14) );
        //c38.setUnderline(0.2f, -2f);
        //Phrase p38 = new Phrase(c38);

        PdfPTable customerDetails = new PdfPTable(2);
        customerDetails.setSpacingBefore(15f);
        customerDetails.setSpacingAfter(10f);

        customerDetails.addCell(p23);
        customerDetails.addCell(p24);

        customerDetails.addCell(p25);
        customerDetails.addCell(p26);

        customerDetails.addCell(p27);
        customerDetails.addCell(p28);

        customerDetails.addCell(p29);
        customerDetails.addCell(p30);

        customerDetails.addCell(p31);
        customerDetails.addCell(p32);

        customerDetails.addCell(p33);
        customerDetails.addCell(p34);

        customerDetails.addCell(p35);
        customerDetails.addCell(p36);

        //customerDetails.addCell(p37);
        //customerDetails.addCell(p38);

        Paragraph paymentInfo = new Paragraph();
        paymentInfo.setAlignment(Paragraph.ALIGN_CENTER);
        Phrase p39 = new Phrase(new Chunk("Make payable to ", FontFactory.getFont(FontFactory.HELVETICA, 14) ));
        Phrase p40 = new Phrase(new Chunk("Blue Kite Press ", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14) ));
        Phrase p41 = new Phrase(new Chunk("(Mail to address above)", FontFactory.getFont(FontFactory.HELVETICA, 14) ));
        Phrase p42 = new Phrase(new Chunk("Allow 7-10 days for delivery", FontFactory.getFont(FontFactory.HELVETICA, 14) ));
        paymentInfo.add(p39);
        paymentInfo.add(p40);
        paymentInfo.add(p41);
        paymentInfo.add(Chunk.NEWLINE);
        paymentInfo.add(p42);
        paymentInfo.add(Chunk.NEWLINE);
        paymentInfo.add(Chunk.NEWLINE);


        ServletContext context = request.getSession().getServletContext();
        com.lowagie.text.Image img = com.lowagie.text.Image.getInstance(context.getRealPath("images/LotusFlower.jpeg"));
        img.scalePercent(66);
        img.setAbsolutePosition(10,635);

        document.add(orderTo);
        document.add(priceSummary);
        document.add(customerDetails);
        document.add(paymentInfo);
        document.add(img);

    }

    private double calculateShipping(int quantity, boolean domestic) {
        double shipping = domestic ? 4.95 : 12.00;
        while(quantity > 1) {
            shipping += domestic ? 2.45 : 6.00;
            quantity--;
        }
        return shipping;
    }

}

