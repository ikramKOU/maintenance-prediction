package ocp.maintenance.prediction.service;

import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;


@Service
public class PdfService {
public ByteArrayInputStream genererPdfDepuisHtml(String html) {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    
    try {
        ITextRenderer renderer = new ITextRenderer();
        
        // Chemin relatif sans "classpath:"
        renderer.getFontResolver().addFont(
            "static/fonts/opensans.ttf", 
            true // embedded
        );
        
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(out);
    } catch (Exception e) {
        // Retry sans police en cas d'échec
        return genererPdfSansPoliceSpecifique(html);
    }
    
    return new ByteArrayInputStream(out.toByteArray());
}

private ByteArrayInputStream genererPdfSansPoliceSpecifique(String html) {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    try {
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(out);
    } catch (Exception e) {
        throw new RuntimeException("Erreur PDF: " + e.getMessage(), e);
    }
    return new ByteArrayInputStream(out.toByteArray());
}
}