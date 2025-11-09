/*
 * [ScrmStd] - 通用SCRM系统
 * Copyright (C) [2025] [张西海]
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.mayanshe.scrmstd.infrastructure.persistence.repo;

import com.mayanshe.scrmstd.generic.support.model.Contact;
import com.mayanshe.scrmstd.generic.support.repo.ContactRepository;
import com.mayanshe.scrmstd.infrastructure.persistence.mapper.ContactMapper;
import com.mayanshe.scrmstd.infrastructure.persistence.po.ContactPo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public class ContactRepositoryImpl implements ContactRepository {
    private final ContactMapper mapper;

    public ContactRepositoryImpl(ContactMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Optional<Contact> load(Long id) {
        ContactPo po = getPo(id);

        return po == null ? Optional.empty() : Optional.of(
                Contact.builder()
                        .id(po.getId())
                        .identity(po.getIdentity())
                        .build()
        );
    }

    @Override
    @Transactional
    public void save(Contact contact) {
        ContactPo po = ContactPo.builder()
                .id(contact.getId())
                .identity(contact.getIdentity())
                .fullName(contact.getFullName())
                .email(contact.getEmail())
                .phone(contact.getPhone())
                .build();

        mapper.insert(po);
    }

    private ContactPo getPo(Long id) {
        if (id == null || id <= 0) {
            return null;
        }

        return mapper.findById(id);
    }
}
