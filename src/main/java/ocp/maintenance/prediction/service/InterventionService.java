// --- InterventionService.java ---
package ocp.maintenance.prediction.service;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

// import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import ocp.maintenance.prediction.dto.InterventionDTO;
import ocp.maintenance.prediction.model.Equipement;
import ocp.maintenance.prediction.model.Intervention;
import ocp.maintenance.prediction.model.StatutIntervention;
import ocp.maintenance.prediction.repository.EquipementRepository;
import ocp.maintenance.prediction.repository.InterventionRepository;

@Service
public class InterventionService {
    private final InterventionRepository interventionRepository;
    private final EquipementRepository equipementRepository;

    public InterventionService(InterventionRepository interventionRepository, EquipementRepository equipementRepository) {
        this.interventionRepository = interventionRepository;
        this.equipementRepository = equipementRepository;
    }

    public List<Intervention> getAllInterventions() {
        return interventionRepository.findAll();
    }

    public Optional<Intervention> getInterventionById(Long id) {
        return interventionRepository.findById(id);
    }

    public Optional<Intervention> findById(Long id) {
    return interventionRepository.findById(id);
}

public Intervention updateIntervention(Long id, Intervention newData) {
    return interventionRepository.findById(id).map(existing -> {
        existing.setDateDebut(newData.getDateDebut());
        existing.setDateFin(newData.getDateFin());
        existing.setDetails(newData.getDetails());
        existing.setStatut(newData.getStatut());
        existing.setEmploye(newData.getEmploye());
        existing.setAlerte(newData.getAlerte()); // si modifiable

        return interventionRepository.save(existing);
    }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Intervention non trouvée"));
}


    @Transactional
    public Intervention saveIntervention(Intervention intervention) {
        return interventionRepository.save(intervention);
    }

  
    @Transactional
    public void deleteIntervention(Long id) {
        interventionRepository.deleteById(id);
    }

    public List<Intervention> getByEmployeId(Long employeId) {
        return interventionRepository.findByEmployeId(employeId);
    }

    public List<Intervention> getByEquipementId(Long equipementId) {
        return interventionRepository.findByEquipementId(equipementId);
    }

    public List<Intervention> getByDateDebutBetween(LocalDateTime start, LocalDateTime end) {
        return interventionRepository.findByDateDebutBetween(start, end);
    }

    public List<Intervention> getInterventionsEnCours() {
        return interventionRepository.findByDateFinIsNull();
    }

    public List<Intervention> getByStatut(StatutIntervention statut) {
        return interventionRepository.findByStatut(statut);
    }

    public List<Intervention> getByEmployeIdAndStatut(Long employeId, StatutIntervention statut) {
        return interventionRepository.findByEmployeIdAndStatut(employeId, statut);
    }

    public Map<String, Long> getStatistiquesGlobales() {
        Map<String, Long> stats = new HashMap<>();
        for (StatutIntervention statut : StatutIntervention.values()) {
            stats.put(statut.name(), interventionRepository.countByStatut(statut));
        }
        return stats;
    }

    public List<InterventionDTO> getActiveInterventions() {
        return interventionRepository.findByStatut(StatutIntervention.EN_COURS)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<InterventionDTO> getHistoricInterventions() {
        return interventionRepository.findByStatut(StatutIntervention.TERMINEE)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public InterventionDTO startIntervention(String equipementName, String motif) {
        Equipement equipement = equipementRepository.findByName(equipementName)
                .orElseThrow(() -> new RuntimeException("Equipement not found"));

        Intervention intervention = new Intervention();
        intervention.setEquipement(equipement);
        intervention.setDetails(motif);
        intervention.setDateDebut(LocalDateTime.now());
        intervention.setStatut(StatutIntervention.TERMINEE
);

        intervention = interventionRepository.save(intervention);

        return mapToDto(intervention);
    }

    @Transactional
    public InterventionDTO closeIntervention(Long id, String rapport) {
        Intervention intervention = interventionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Intervention not found"));

        intervention.setDetails(rapport);
        intervention.setDateFin(LocalDateTime.now());
        intervention.setStatut(StatutIntervention.TERMINEE);

        intervention = interventionRepository.save(intervention);

        return mapToDto(intervention);
    }

    private InterventionDTO mapToDto(Intervention intervention) {
    InterventionDTO dto = new InterventionDTO();
    
    dto.setDateDebut(intervention.getDateDebut() != null ? intervention.getDateDebut().toString() : null);
    dto.setDateFin(intervention.getDateFin() != null ? intervention.getDateFin().toString() : null);
    dto.setDetails(intervention.getDetails());
    dto.setStatut(intervention.getStatut() != null ? intervention.getStatut().name() : null);
    dto.setEmploye(intervention.getEmploye() != null ? intervention.getEmploye().getId() : null);
    dto.setEquipement(intervention.getEquipement() != null ? intervention.getEquipement().getId() : null);
    dto.setAlerte(intervention.getAlerte() != null ? intervention.getAlerte().getId() : null);

    return dto;
}
//  public String creerRapport(Intervention intervention) {
//     StringBuilder sb = new StringBuilder();
//     sb.append("----- RAPPORT D'INTERVENTION -----\n");
//     sb.append("ID : ").append(intervention.getId()).append("\n");
//     sb.append("Début : ").append(intervention.getDateDebut()).append("\n");
//     sb.append("Fin : ").append(intervention.getDateFin()).append("\n");
//     sb.append("Statut : ").append(intervention.getStatut()).append("\n");
//     sb.append("Détails : ").append(intervention.getDetails()).append("\n");

//     if (intervention.getAlerte() != null) {
//         sb.append("Alerte liée : #").append(intervention.getAlerte().getId())
//           .append(" - ").append(intervention.getAlerte().getMessage()).append("\n");
//     }
//     if (intervention.getEmploye() != null) {
//         sb.append("Technicien : ").append(intervention.getEmploye().getNom())
//           .append(" (").append(intervention.getEmploye().getUsername()).append(")\n");
//     }
//     if (intervention.getEquipement() != null) {
//         sb.append("Équipement : ").append(intervention.getEquipement().getName())
//           .append(" (État : ").append(intervention.getEquipement().getEtat()).append(")\n");
//     }

//     return sb.toString();
//  }
public String creerRapportHTML(Intervention intervention) {
    String logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/2/25/OCP_Logo.svg/512px-OCP_Logo.svg.png";

    StringBuilder sb = new StringBuilder();
    sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
    sb.append("<html lang='fr' xmlns='http://www.w3.org/1999/xhtml'>");
    sb.append("<head>");
    sb.append("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />");
    sb.append("<style>");
    sb.append("body { font-family: Arial, sans-serif; color: #333; margin: 40px; }");
    sb.append("header { display: flex; justify-content: space-between; align-items: center; border-bottom: 2px solid #2E86C1; padding-bottom: 10px; margin-bottom: 30px; }");
    sb.append("header img { height: 60px; }");
    sb.append("h1 { color: rgb(46, 193, 73); font-size: 24px; margin: 0; }");
    sb.append("table { width: 100%; border-collapse: collapse; margin-top: 20px; }");
    sb.append("th, td { border: 1px solid #ddd; padding: 8px; }");
    sb.append("th { background-color: rgb(60, 197, 47); color: white; text-align: left; }");
    sb.append(".footer { border-top: 1px solid #ccc; margin-top: 40px; padding-top: 10px; font-size: 12px; color: #777; text-align: center; }");
    sb.append(".signature { margin-top: 60px; }");
    sb.append(".signature img { max-height: 50px; }");
    sb.append(".badge { display: inline-block; padding: 4px 10px; border-radius: 4px; color: white; font-size: 12px; }");
    sb.append(".badge-ouverte { background-color: #e74c3c; }");
    sb.append(".badge-en-cours { background-color: #f1c40f; }");
    sb.append(".badge-terminee { background-color: #2ecc71; }");
    sb.append("</style>");
    sb.append("</head>");
    sb.append("<body>");

    // Header
    sb.append("<header>");
    sb.append("<div><img src='file:src/main/resources/static/img/ocp_logo.png' alt='Logo OCP' style='margin-right: 20px;' /></div>");  

    sb.append("<div><h1>Rapport d'intervention 2025 </h1></div>");
      sb.append("</header>");

    // Table
    sb.append("<table>");
    sb.append("<tr><th>Champ</th><th>Valeur</th></tr>");
    sb.append("<tr><td><strong>ID</strong></td><td>").append(intervention.getId()).append("</td></tr>");
    sb.append("<tr><td><strong>Début</strong></td><td>").append(intervention.getDateDebut()).append("</td></tr>");
    sb.append("<tr><td><strong>Fin</strong></td><td>").append(intervention.getDateFin()).append("</td></tr>");
    
    sb.append("<tr><td><strong>Statut</strong></td><td>");
    sb.append("<span class='badge badge-")
      .append(intervention.getStatut().name().toLowerCase().replace("_", "-"))
      .append("'>")
      .append(intervention.getStatut())
      .append("</span>");
    sb.append("</td></tr>");

    sb.append("<tr><td><strong>Détails</strong></td><td>")
      .append(intervention.getDetails() != null ? intervention.getDetails() : "-")
      .append("</td></tr>");

    if (intervention.getAlerte() != null) {
        sb.append("<tr><td><strong>Alerte liée</strong></td><td>#")
          .append(intervention.getAlerte().getId())
          .append(" - ")
          .append(intervention.getAlerte().getMessage())
          .append("</td></tr>");
    }
    if (intervention.getEmploye() != null) {
        sb.append("<tr><td><strong>Technicien</strong></td><td>")
          .append(intervention.getEmploye().getNom())
          .append(" (")
          .append(intervention.getEmploye().getUsername())
          .append(")")
          .append("</td></tr>");
    }
    if (intervention.getEquipement() != null) {
        sb.append("<tr><td><strong>Équipement</strong></td><td>")
          .append(intervention.getEquipement().getName())
          .append(" (État : ")
          .append(intervention.getEquipement().getEtat())
          .append(")")
          .append("</td></tr>");
    }
    sb.append("</table>");

    // Signature block
    sb.append("<div class='signature'>");
    sb.append("<p><strong>Signature :</strong></p>");

    sb.append("<p>______________________________</p>");
    // Exemple avec image de signature si souhaité :
    // sb.append("<img src='https://example.com/signature.png' alt='Signature' />");
    sb.append("</div>");
        sb.append("<div>");

        sb.append("</div>");



    // Footer
    sb.append("<div class='footer' style='margin-top: 400px;'>");
    sb.append(" ").append("LA laverie Bengeurir OCP Group 2025");
    sb.append("</div>");

    sb.append("</body></html>");

    return sb.toString();
}


// public String creerRapportHTML(Intervention intervention) {
//     StringBuilder sb = new StringBuilder();
//     sb.append("<html><body style='font-family:Arial,sans-serif;'>");
//     sb.append("<h2 style='color:#2E86C1;'>Rapport d'intervention</h2>");
//     sb.append("<p><strong>ID :</strong> ").append(intervention.getId()).append("</p>");
//     sb.append("<p><strong>Début :</strong> ").append(intervention.getDateDebut()).append("</p>");
//     sb.append("<p><strong>Fin :</strong> ").append(intervention.getDateFin()).append("</p>");
//     sb.append("<p><strong>Statut :</strong> ").append(intervention.getStatut()).append("</p>");
//     sb.append("<p><strong>Détails :</strong> ").append(intervention.getDetails()).append("</p>");

//     if (intervention.getAlerte() != null) {
//         sb.append("<p><strong>Alerte liée :</strong> #").append(intervention.getAlerte().getId())
//           .append(" - ").append(intervention.getAlerte().getMessage()).append("</p>");
//     }
//     if (intervention.getEmploye() != null) {
//         sb.append("<p><strong>Technicien :</strong> ").append(intervention.getEmploye().getNom())
//           .append(" (").append(intervention.getEmploye().getUsername()).append(")</p>");
//     }
//     if (intervention.getEquipement() != null) {
//         sb.append("<p><strong>Équipement :</strong> ").append(intervention.getEquipement().getName())
//           .append(" (État : ").append(intervention.getEquipement().getEtat()).append(")</p>");
//     }
//     sb.append("</body></html>");
//     return sb.toString();
// }
public void save(Intervention intervention) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'save'");
}


 
// public ByteArrayInputStream genererPdfRapport(Intervention intervention) {
//     String rapportHtml = creerRapportHTML(intervention);

//     Document document = new Document();
//     ByteArrayOutputStream out = new ByteArrayOutputStream();

//     try {
//         PdfWriter writer = PdfWriter.getInstance(document, out);
//         document.open();

//         // Convertir le HTML en PDF avec XMLWorker
//         XMLWorkerHelper.getInstance().parseXHtml(
//             writer, 
//             document, 
//             new ByteArrayInputStream(rapportHtml.getBytes(StandardCharsets.UTF_8))
//         );

//         document.close();
//     } catch (Exception e) {
//         e.printStackTrace();
//     }

//     return new ByteArrayInputStream(out.toByteArray());
// }





    public List<Intervention> filtrerInterventions(String statut, Long employeId, Long equipementId) {
    List<Intervention> interventions = interventionRepository.findAll();

    if (statut != null) {
        interventions = interventions.stream()
            .filter(i -> i.getStatut().toString().equalsIgnoreCase(statut))
            .collect(Collectors.toList());
    }

    if (employeId != null) {
        interventions = interventions.stream()
            .filter(i -> i.getEmploye() != null && i.getEmploye().getId().equals(employeId))
            .collect(Collectors.toList());
    }

    if (equipementId != null) {
        interventions = interventions.stream()
            .filter(i -> i.getEquipement() != null && i.getEquipement().getId().equals(equipementId))
            .collect(Collectors.toList());
    }

    return interventions;
}



}
