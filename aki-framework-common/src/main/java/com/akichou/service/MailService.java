package com.akichou.service;

/**
 * Mail Service Interface
 *
 * @author Aki Chou
 * @date 2024/07/26 Fri.
 */
public interface MailService {

    void sendMailForForgettingPassword(String email);
}
