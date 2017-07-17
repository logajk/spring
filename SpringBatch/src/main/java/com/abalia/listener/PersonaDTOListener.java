package com.abalia.listener;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.abalia.model.PersonaDTO;

@Component
public class PersonaDTOListener extends JobExecutionListenerSupport {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/* (non-Javadoc)
	 * @see org.springframework.batch.core.listener.JobExecutionListenerSupport#afterJob(org.springframework.batch.core.JobExecution)
	 */
	@Override
	public void afterJob(JobExecution jobExecution) {
		// TODO Auto-generated method stub
		if(jobExecution.getStatus() == BatchStatus.COMPLETED){
			log.debug("!!! JOBDTO TERMINADO! Es hora de verificar el resultado ");
			
			List<PersonaDTO> listado = jdbcTemplate.query("SELECT nombre, apellidos, fecha FROM persona", new RowMapper<PersonaDTO>(){

				@Override
				public PersonaDTO mapRow(ResultSet rs, int row) throws SQLException {
					// TODO Auto-generated method stub
					return new PersonaDTO(rs.getString(1), rs.getString(2), rs.getDate(3));
				}
				
			});
			for(PersonaDTO p : listado){
				log.debug("Encontrado <" + p + "> en la BB.DD.");
			}
		}
	}
}
