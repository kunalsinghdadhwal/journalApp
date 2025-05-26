package com.kunal.journalApp.scheduler;

import com.kunal.journalApp.cache.AppCache;
import com.kunal.journalApp.entity.JournalEntry;
import com.kunal.journalApp.entity.User;
import com.kunal.journalApp.repository.UserRepositoryImpl;
import com.kunal.journalApp.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepository;
    @Autowired
    private AppCache appCache;


    @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUsersAndSendSAMail(){
        List<User> users = userRepository.getUserFroSA();
        for(User user : users){
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<JournalEntry> filteredEntries = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).collect(Collectors.toList());
            String entry = String.join(" " + filteredEntries);
            String sentiment = sentimentAnalysisService.getSentiment(entry);
            emailService.sendEmail(user.getEmail(), "Sentiment for last 7 days", sentiment);
        }
    }

    @Scheduled(cron = "0 0 0 * * SUN")
    public void clearAppCache() {
        appCache.init();
    }
}
