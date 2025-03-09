package com.cine.reservation.service;

import com.cine.reservation.dto.ReservationDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.*;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    private static final String FROM_EMAIL = "oscarjairsolanoguzman@gmail.com";

    public void sendReservationEmail(String toEmail, ReservationDTO reservationDTO) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(FROM_EMAIL);
            helper.setTo(toEmail);
            helper.setSubject("Confirmación de Reservación - Cine");
            helper.setText("""
                <h2>¡Tu reservación ha sido confirmada!</h2>
                <p><b>Película:</b> %s</p>
                <p><b>Horario:</b> %s</p>
                <p><b>Sala:</b> %d</p>
                <p><b>Asientos:</b> %s</p>
                <p>Gracias por reservar con nosotros.</p>
                """.formatted(
                    reservationDTO.getMovieId(),
                    reservationDTO.getSchedule(),
                    reservationDTO.getRoomId(),
                    String.join(", ", reservationDTO.getSeats())
            ), true);

            mailSender.send(message);
            System.out.println("Correo enviado a: " + toEmail);

        } catch (MessagingException e) {
            System.err.println("Error al enviar correo: " + e.getMessage());
        }
    }
}
